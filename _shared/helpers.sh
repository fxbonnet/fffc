#!/bin/bash

function validate_exit_code()
{
    local CODE="$1"
    local MGS="$2"
    local SILENT=$3

    if [ -z "$CODE" ]; then
        echo 'first parameter is required; the exit code $?'
        exit -1
    fi

    if [ -z "$MGS" ]; then
        echo 'second parameter is required; the message'
        exit -1
    fi

    # silent check is useful if bash functions return value
    if [ -z "$SILENT" ]; then
      [ $CODE -eq 0 ] && echo " [OK] - $MGS, continue..."
    fi

    [ $CODE -ne 0 ] && echo " [ERROR] - [$CODE] - $MGS, exiting with code: $CODE" && exit $CODE
}