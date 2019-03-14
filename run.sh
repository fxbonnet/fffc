#!/bin/bash

SCRIPTDIR=`/usr/bin/dirname $0`
if [ "$SCRIPTDIR" != "." ]; then
  cd $SCRIPTDIR
fi
SCRIPTDIR=`/bin/pwd`

# select to use python3
PYTHON=python
which $PYTHON > /dev/null 2>&1
rc=$?
if [ "$rc" != "0" ]; then
  echo "Can't find python to run this tool. Exit.'"
  exit 1
else
  version=`$PYTHON -V 2>&1 | awk '{print $2}'`
  if ! `echo $version | egrep "^3" >/dev/null`; then
    echo "Default version of python is version: $version. Try to switch to version 3."
    PYTHON=python3
    which $PYTHON > /dev/null 2>&1
    rc=$?
    if [ "$rc" != "0" ]; then
      echo "Can't find python3 to run this tool. Exit.'"
      exit 2
    else
      echo "Switch to version `$PYTHON -V 2>&1 | awk '{print $2}'` to run this tool."
      echo
    fi
  fi
fi


dataDir="$SCRIPTDIR/testcases"
outputDir="$SCRIPTDIR/output"
mkdir -p $outputDir
rm -f $outputDir/case*.csv

for casedir in `ls $dataDir`;
do
  $PYTHON -u converter.py -m "$dataDir/$casedir/metadata" -d "$dataDir/$casedir/data" -o "$outputDir/$casedir.csv"
done