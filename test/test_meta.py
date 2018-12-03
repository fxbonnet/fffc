import pytest
from fffc import fffc


def test_lentest(tmpdir):

    metafile = tmpdir.join("lentest")
    metafile.write("Birth date,10,date\nFirst name,15,string,t\nLast name,15,string\nWeight,5,numeric")

    with pytest.raises(ValueError):
        client = fffc.fffc()
        client.__init__()
        client.metaFile = str(metafile)
        client.parsemeta()


def test_valtest(tmpdir):

    metafile = tmpdir.join("valtest")
    metafile.write("Birth date,10,date\nFirst name,15,string\nLast name,15,sdtring\nWeight,5,numeric")

    with pytest.raises(ValueError):
        client = fffc.fffc()
        client.__init__()
        client.metaFile = str(metafile)
        client.parsemeta()
