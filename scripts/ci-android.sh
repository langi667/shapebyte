#!/bin/bash
script_path=$(realpath "$0")
script_directory=$(dirname "$script_path")
cd "$script_directory/../" || exit 200

source "$script_directory/core/logging.sh"
source "$script_directory/core/error_codes.sh"

### Gradle Update ###
andLogI "Updating Project via gradle ..."
./gradlew --refresh-dependencies

return_code=$?
if [ $return_code -ne 0 ]; then
  # shellcheck disable=SC2086
  exit $ERROR_ANDROID_GRADLE_UPDATE_FAILED
fi


### Lint / Detekt ###
"$script_directory/lint-android.sh"
return_code=$?
if [ $return_code -ne 0 ]; then
  # shellcheck disable=SC2086
  exit $ERROR_ANDROID_LINT_FAILED
fi

### Unit tests ###
"$script_directory/unit_tests-android.sh"
return_code=$?
if [ $return_code -ne 0 ]; then
  # shellcheck disable=SC2086
  exit $ERROR_ANDROID_UNIT_TEST_FAILED
fi

### Android instrumented tests ###
"$script_directory/android-tests.sh"
return_code=$?
if [ $return_code -ne 0 ]; then
  # shellcheck disable=SC2086
  exit $ERROR_ANDROID_INSTRUMENTED_TESTS_FAILED
fi

### Android snapshot tests ###
"$script_directory/snapshot_tests-android.sh"
return_code=$?
if [ $return_code -ne 0 ]; then
  # shellcheck disable=SC2086
  exit $ERROR_ANDROID_SNAPSHOT_TESTS_FAILED
fi

exit 0
