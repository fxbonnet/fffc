import pytest
import sys, os
sys.path.append(os.path.join(os.path.dirname(__file__), '..', 'src', 'utils'))

import ConvertValueToType as cv

def test_default_value():
    cfv = cv.ConvertValueToType("test_string", "string")
    assert cfv.value == "test_string"

def test_default_type():
    cfv = cv.ConvertValueToType("test_string", "string")
    assert cfv.column_type == "string"

def test_validate_string():
    cfv = cv.ConvertValueToType("test_string's", "string")
    ret = cfv.convert()
    assert ret == str("test_string's")

def test_validate_date():
    cfv = cv.ConvertValueToType("1970-01-01", "date")
    ret = cfv.convert()
    assert ret == "01/01/1970"

def test_validate_numeric():
    cfv = cv.ConvertValueToType("105.10", "numeric")
    ret = cfv.convert()
    assert ret == "105.10"

def test_exception_invalid_type():
    cfv = cv.ConvertValueToType("105.10", "numeric")
    ret = cfv.convert()
    assert ret == "105.10"