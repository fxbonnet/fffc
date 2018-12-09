#!/bin/bash

source './_shared/helpers.sh'

CONTAINER_TAG="octo-fffc:dev"

# default coverage for simplecov
# less than this will fail unit tests
COVERAGE="${95:-$1}"

# build a new container
docker build . --tag $CONTAINER_TAG
validate_exit_code $? "building $CONTAINER_TAG image"

# run our container
docker run --rm -it \
        -v "$(pwd)/src:/app" \
        -e OCTO_FFFC_SIMPLECOV_COVERAGE=$COVERAGE \
        $CONTAINER_TAG \
        sh docker-run-tests.sh
validate_exit_code $? "running tests"