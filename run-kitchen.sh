#!/bin/bash

source './_shared/helpers.sh'

sh -c "cd tests/integration && kitchen test -p"
validate_exit_code $? "Kitchen tests"

exit 0