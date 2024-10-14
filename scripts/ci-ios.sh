#!/bin/bash
script_path=$(realpath "$0")
script_directory=$(dirname "$script_path")
cd "$script_directory/../" || exit 200

source "$script_directory/core/logging.sh"
source "$script_directory/core/error_codes.sh"

### Compile all Environments ###
"$script_directory/compile-all-environments-ios.sh"
return_code=$?
if [ $return_code -ne 0 ]; then
  exit $return_code
fi

### Unit tests ###
"$script_directory/unit_tests-ios.sh"
return_code=$?
if [ $return_code -ne 0 ]; then
  # shellcheck disable=SC2086
  exit $ERROR_IOS_UNIT_TEST_FAILED
fi

