#!/usr/bin/env bash

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
OPENAPI_FILE="${SCRIPT_DIR}/../../openapi.yml"

npx @redocly/cli preview-docs ${OPENAPI_FILE}
