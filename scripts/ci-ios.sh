#!/bin/bash
script_path=$(realpath "$0")
script_directory=$(dirname "$script_path")
cd "$script_directory/../"

source $script_directory/core/logging.sh
source $script_directory/core/error_codes.sh

### Lint / Detekt ###
"$script_directory/lint-ios.sh"
return_code=$?
if [ $return_code -ne 0 ]; then
  exit $ERROR_IOS_LINT_FAILED
fi

### Unit tests ###
"$script_directory/unit_tests-ios.sh"
return_code=$?
if [ $return_code -ne 0 ]; then
  exit $ERROR_IOS_UNIT_TEST_FAILED
fi

