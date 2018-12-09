#!/bin/bash

source './_shared/helpers.sh'

CONTAINER_TAG="harmjanblok/rubycritic:latest"

docker run --rm \
        -v $(pwd)/src/octo-fffc/lib:/usr/src/app \
        $CONTAINER_TAG

validate_exit_code $? "running rubycritic, tag: $CONTAINER_TAG"

exit 0