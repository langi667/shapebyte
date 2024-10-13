#!/bin/bash
script_path=$(realpath "$0")
script_directory=$(dirname "$script_path")
cd "$script_directory/../"

source $script_directory/core/logging.sh
source $script_directory/core/error_codes.sh

### Lint / Detekt ###
"$script_directory/lint-android.sh"
return_code=$?
if [ $return_code -ne 0 ]; then
  exit $ERROR_ANDROID_LINT_FAILED
fi

### Unit tests ###
"$script_directory/unit_tests-android.sh"
return_code=$?
if [ $return_code -ne 0 ]; then
  exit $ERROR_ANDROID_UNIT_TEST_FAILED
else
  exit 0
fi

