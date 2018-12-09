#!/bin/bash

echo 'Building gem...'
source './_shared/helpers.sh'
source './run-build-gem.sh'
validate_exit_code $? "building gem"

CONTAINER_TAG="octo-fffc:tool"

# create tool container
docker build . \
        -t $CONTAINER_TAG \
        -f Dockerfile.tool
validate_exit_code $? "building octo-fffc tooling container"

# testing 
docker run --rm -it \
        -v $(pwd)/src/octo-fffc/spec/octo/data/sets/01-simple:/app \
        $CONTAINER_TAG \
        octo-fffc -i /app/data.txt -d /app/metadata.csv -o /app/.output/output.csv 
validate_exit_code $? "running octo-fffc tool container"

exit 0