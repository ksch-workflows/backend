#!/usr/bin/env bash

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

cd $SCRIPT_DIR

asciidoctor --base-dir ../../build/generated-snippets  --out-file ../../docs/index.html index.adoc
