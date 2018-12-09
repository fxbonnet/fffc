#!/bin/bash

source './_shared/helpers.sh'

gem install rufo
validate_exit_code $? "install rufo"

rufo $(pwd)/src/octo-fffc/lib
validate_exit_code $? "running rufo"

exit 0