#!/bin/bash
SCRIPT_DIR="$(dirname "$(realpath "$0")")"
echo $SCRIPT_DIR
(cd $SCRIPT_DIR/../.. && tr -d "\r" < gradlew && chmod +x gradlew && sh ./gradlew run)
