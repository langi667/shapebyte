#!/bin/bash
script_path=$(realpath "$0")
script_directory=$(dirname "$script_path")
cd "$script_directory/../" || exit 200

source "$script_directory/core/logging.sh"
source $script_directory/core/error_codes.sh

### Lint / Detekt ###
"$script_directory/lint-shared.sh"
return_code=$?
if [ $return_code -ne 0 ]; then
  exit $ERROR_SHARED_LINT_FAILED
fi

### Unit tests ###
"$script_directory/unit_tests-common.sh"
return_code=$?
if [ $return_code -ne 0 ]; then
  exit $ERROR_SHARED_UNIT_TEST_FAILED
fi

