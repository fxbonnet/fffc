import pytest
import sys, os
sys.path.append(os.path.join(os.path.dirname(__file__), '..', 'src', 'utils'))

import MetaFileVaIidator as mfv

def test_default_file_name():
    mf = mfv.MetaFileValidator("test_file.csv")
    assert mf.file == "test_file.csv"

def test_validate_string():
    mf = mfv.MetaFileValidator("input/metadata.csv")
    mf.validate()
    assert mf.column_names[1][1].strip() == "string"

def test_validate_date():
    mf = mfv.MetaFileValidator("input/metadata.csv")
    mf.validate()
    assert mf.column_names[0][1].strip() == "date"

def test_validate_numeric():
    mf = mfv.MetaFileValidator("input/metadata.csv")
    mf.validate()
    assert mf.column_names[3][1].strip() == "numeric"

def test_exception_invalid_type():
    mf = mfv.MetaFileValidator("input/invalid_metadata.csv")
    with pytest.raises(Exception):
        mf.validate()