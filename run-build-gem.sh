#!/bin/bash

source './_shared/helpers.sh'

CONTAINER_TAG="octo-fffc:dev"

# build a new container
docker build . --tag $CONTAINER_TAG
validate_exit_code $? "building $CONTAINER_TAG image"

# run our container
docker run --rm -it \
        -v "$(pwd)/src:/app" \
        $CONTAINER_TAG \
        bash docker-build-gem.sh