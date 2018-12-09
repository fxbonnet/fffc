#!/bin/bash

source './_shared/helpers.sh'

CONTAINER_TAG="cagedata/rubocop:latest"

docker run --rm \
        -v $(pwd)/src/octo-fffc:/app \
        $CONTAINER_TAG

validate_exit_code $? "running rubocop, tag: $CONTAINER_TAG"

exit 0