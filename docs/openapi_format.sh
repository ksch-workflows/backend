#!/usr/bin/env bash

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

npx openapi-format ${SCRIPT_DIR}/openapi.yml --output ${SCRIPT_DIR}/openapi.yml --sortComponentsFile ${SCRIPT_DIR}/sortComponents.json --casingFile ${SCRIPT_DIR}/customCasing.yml

# --casingFile ${SCRIPT_DIR}/casing.json
# --configFile ${SCRIPT_DIR}/config.json