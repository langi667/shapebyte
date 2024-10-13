#!/bin/bash
script_path=$(realpath "$0")
script_directory=$(dirname "$script_path")
cd "$script_directory/../" || exit 200

source "$script_directory/core/logging.sh"

sharedLogI "👩‍🔬 Start unit tests ..."
./gradlew :shared:connectedAndroidTest :shared:iosSimulatorArm64Test

return_code=$?
if [ $return_code -eq 0 ]; then
  sharedLogS "Running unit tests successful 😊"
else
  sharedLogE "Running unit tests failed 😞"
  exit $return_code
fi