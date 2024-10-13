#!/bin/bash
script_path=$(realpath "$0")
script_directory=$(dirname "$script_path")
cd "$script_directory/../"

source $script_directory/core/logging.sh

log_error() {
  logE "ERROR" "$1"
}

log_success() {
  logS "SUCCESS" "CI checks completed successfully ☺️"
}

run() {
  script_name=$1
  $script_directory/$script_name
  return_code=$?

  if [ $return_code -ne 0 ]; then
    log_error "Unable to perform $script_name 😞 ! It returned with $return_code"
    exit $return_code
  fi
}

run ci-shared.sh

run ci-android.sh &
pid_ci_android=$!

run ci-ios.sh &
pid_ci_ios=$!

wait $pid_ci_android
return_android=0

wait $pid_ci_ios
return_ios=$?

echo "hier"
# Evaluate the return codes
if [ $return_android -ne 0 ]; then
    log_error "CI checks for Android failed with return code $return_android 😣"
fi

if [ $return_ios -ne 0 ]; then
    log_error "CI checks for iOS failed with return code $return_ios 😣"
fi

if [ $return_android -eq 0 ] && [ $return_ios -eq 0 ]; then
    log_success
fi