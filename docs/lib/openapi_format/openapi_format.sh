#!/usr/bin/env bash

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
OPENAPI_FILE="${SCRIPT_DIR}/../../openapi.yml"

npx openapi-format ${OPENAPI_FILE} \
  --output ${OPENAPI_FILE} \
  --sortComponentsFile ${SCRIPT_DIR}/sortComponents.json \
  --casingFile ${SCRIPT_DIR}/customCasing.yml
