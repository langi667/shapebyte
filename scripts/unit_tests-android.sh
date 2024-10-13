#!/bin/bash
script_path=$(realpath "$0")
script_directory=$(dirname "$script_path")
cd "$script_directory/../" || exit 200

source "$script_directory/core/logging.sh"

andLogI "Start unit tests for Android ..."
./gradlew androidApp:testDebugUnitTest

return_code=$?
if [ $return_code -eq 0 ]; then
  andLogS "Running unit tests successful 😊"
else
  andLogE "Running unit tests failed 😞"
  exit $return_code
fi