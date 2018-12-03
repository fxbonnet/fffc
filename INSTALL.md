## Solution
The proposed solution requires Python 3. Further dependencies are included in the requirements.txt file. Tests are included in tests/ and can be executed as follows:

```
python3 setup.py test
/usr/local/lib/python3.7/site-packages/setuptools/dist.py:470: UserWarning: Normalizing '0.1dev' to '0.1.dev0'
  normalized_version,
running pytest
running egg_info
writing fffc.egg-info/PKG-INFO
writing dependency_links to fffc.egg-info/dependency_links.txt
writing top-level names to fffc.egg-info/top_level.txt
reading manifest file 'fffc.egg-info/SOURCES.txt'
writing manifest file 'fffc.egg-info/SOURCES.txt'
running build_ext
========================================================= test session starts =========================================================
platform darwin -- Python 3.7.0, pytest-4.0.1, py-1.7.0, pluggy-0.8.0
rootdir: /Users/kobus/code/python/FixedFileFormatConverter, inifile:
collected 5 items

test/test_meta.py ..                                                                                                            [ 40%]
test/test_output.py ...                                                                                                         [100%]

========================================================== warnings summary ===========================================================
/usr/local/lib/python3.7/site-packages/pandas/core/dtypes/inference.py:6
  /usr/local/lib/python3.7/site-packages/pandas/core/dtypes/inference.py:6: DeprecationWarning: Using or importing the ABCs from 'collections' instead of from 'collections.abc' is deprecated, and in 3.8 it will stop working
    from collections import Iterable

/usr/local/lib/python3.7/site-packages/pandas/core/tools/datetimes.py:3
  /usr/local/lib/python3.7/site-packages/pandas/core/tools/datetimes.py:3: DeprecationWarning: Using or importing the ABCs from 'collections' instead of from 'collections.abc' is deprecated, and in 3.8 it will stop working
    from collections import MutableMapping

-- Docs: https://docs.pytest.org/en/latest/warnings.html
================================================ 5 passed, 2 warnings in 0.46 seconds =================================================
```

To install the package:
```
python3 setup.py install
/usr/local/lib/python3.7/site-packages/setuptools/dist.py:470: UserWarning: Normalizing '0.1dev' to '0.1.dev0'
  normalized_version,
running install
running bdist_egg
running egg_info
writing fffc.egg-info/PKG-INFO
writing dependency_links to fffc.egg-info/dependency_links.txt
writing top-level names to fffc.egg-info/top_level.txt
reading manifest file 'fffc.egg-info/SOURCES.txt'
writing manifest file 'fffc.egg-info/SOURCES.txt'
installing library code to build/bdist.macosx-10.13-x86_64/egg
running install_lib
running build_py
copying fffc/fffc.py -> build/lib/fffc
creating build/bdist.macosx-10.13-x86_64/egg
creating build/bdist.macosx-10.13-x86_64/egg/fffc
copying build/lib/fffc/run.py -> build/bdist.macosx-10.13-x86_64/egg/fffc
copying build/lib/fffc/fffc.py -> build/bdist.macosx-10.13-x86_64/egg/fffc
copying build/lib/fffc/__init__.py -> build/bdist.macosx-10.13-x86_64/egg/fffc
byte-compiling build/bdist.macosx-10.13-x86_64/egg/fffc/run.py to run.cpython-37.pyc
byte-compiling build/bdist.macosx-10.13-x86_64/egg/fffc/fffc.py to fffc.cpython-37.pyc
byte-compiling build/bdist.macosx-10.13-x86_64/egg/fffc/__init__.py to __init__.cpython-37.pyc
creating build/bdist.macosx-10.13-x86_64/egg/EGG-INFO
installing scripts to build/bdist.macosx-10.13-x86_64/egg/EGG-INFO/scripts
running install_scripts
running build_scripts
creating build/bdist.macosx-10.13-x86_64/egg/EGG-INFO/scripts
copying build/scripts-3.7/fffc -> build/bdist.macosx-10.13-x86_64/egg/EGG-INFO/scripts
changing mode of build/bdist.macosx-10.13-x86_64/egg/EGG-INFO/scripts/fffc to 755
```
To use the package:
```
hostname  » ll
total 12K
-rw-r--r--   1 kobus staff   77 Dec  2 23:23 metafile
-rw-r--r--   1 kobus staff  186 Dec  3 11:46 inputfile
drwxr-xr-x   5 kobus staff  160 Dec  3 11:46 .
drwxr-xr-x+ 89 kobus staff 2.8K Dec  3 12:01 ..

hostname  » which fffc
/usr/local/bin/fffc

hostname  » fffc -i inputfile -m metafile -o outputfile.csv
hostname  » cat outputfile.csv
Birth date,First name,Last name,Weight
01/01/1970,John,Smith,81.5
31/01/1975,Ÿane,Doe,61.1
28/11/1988,Bob,"B,!g",102.0

hostname  » ll
total 12K
-rw-r--r--   1 kobus staff   77 Dec  2 23:23 metafile
-rw-r--r--   1 kobus staff  186 Dec  3 11:46 inputfile
drwxr-xr-x   5 kobus staff  160 Dec  3 12:01 .
-rw-r--r--   1 kobus staff  152 Dec  3 12:01 outputfile.csv
drwxr-xr-x+ 89 kobus staff 2.8K Dec  3 12:02 ..
```
