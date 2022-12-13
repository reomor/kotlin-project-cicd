#!/bin/bash
SCRIPT_DIR="$(dirname "$(realpath "$0")")"
echo $SCRIPT_DIR
(cd $SCRIPT_DIR/../.. && chmod +x gradlew && sh ./gradlew clean build)
