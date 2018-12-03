import pytest
from fffc import fffc


def test_quotetest(tmpdir):

    metafile = tmpdir.join("metafile")
    metafile.write("Birth date,10,date\nFirst name,15,string\nLast name,15,string\nWeight,5,numeric")

    infile = tmpdir.join("quotetest")
    infile.write("1988-11-28Bob            B,!g            102.4")

    outfile = tmpdir.join("outtest")

    client = fffc.fffc()
    client.__init__()
    client.metaFile = str(metafile)
    client.input = str(infile)
    client.output = str(outfile)
    client.parsemeta()
    client.parsefixedfile()

    with open(outfile) as f:
        lines = f.readlines()

        assert len(lines) == 2
        assert lines[0] == "Birth date,First name,Last name,Weight\n"
        assert lines[1] == "28/11/1988,Bob,\"B,!g\",102.0\n"


def test_emptyvals(tmpdir):

    metafile = tmpdir.join("metafile")
    metafile.write("Birth date,10,date\nFirst name,15,string\nLast name,15,string\nWeight,5,numeric")

    infile = tmpdir.join("quotetest")
    infile.write("1988-11-28Bob            B,!g            102.4\n\n\n1975-01-31Jane           Doe             61.1")

    outfile = tmpdir.join("outtest")

    with pytest.raises(ValueError):
        client = fffc.fffc()
        client.__init__()
        client.metaFile = str(metafile)
        client.input = str(infile)
        client.output = str(outfile)
        client.parsemeta()
        client.parsefixedfile()

    # with open(outfile) as f:
    #     lines = f.readlines()
    #
    #     assert len(lines) == 3
    #     assert lines[0] == "Birth date,First name,Last name,Weight\n"
    #     assert lines[1] == "28/11/1988,Bob,\"B,!g\",102.0\n"
    #     assert lines[2] == "31/01/1975,Jane,Doe,61.1\n"


def test_specialchars(tmpdir):

    metafile = tmpdir.join("metafile")
    metafile.write("Birth date,10,date\nFirst name,15,string\nLast name,15,string\nWeight,5,numeric")

    infile = tmpdir.join("quotetest")
    infile.write("1988-11-28Bøb            B,!g            102.4\n1975-01-31¥ane           Doe§             50")

    outfile = tmpdir.join("outtest")

    client = fffc.fffc()
    client.__init__()
    client.metaFile = str(metafile)
    client.input = str(infile)
    client.output = str(outfile)
    client.parsemeta()
    client.parsefixedfile()

    with open(outfile) as f:
        lines = f.readlines()

        assert len(lines) == 3
        assert lines[0] == "Birth date,First name,Last name,Weight\n"
        assert lines[1] == "28/11/1988,Bøb,\"B,!g\",102.0\n"
        assert lines[2] == "31/01/1975,¥ane,Doe§,50.0\n"
