#!/bin/bash
script_path=$(realpath "$0")
script_directory=$(dirname "$script_path")
cd "$script_directory/../" || exit 200

source "$script_directory/core/logging.sh"
source "$script_directory/core/ios-settings.sh"

iOSLogI "👩‍🔬 Start unit tests ..."
xcodebuild -project ./iosApp/iosApp.xcodeproj test -scheme Development -destination "platform=iOS Simulator,name=$IOS_SIM_DEVICE,OS=$IOS_SIM_OS" -testPlan "UnitTests"

return_code=$?
if [ $return_code -eq 0 ]; then
  iOSLogS "Running unit tests successful 😊"
else
  iOSLogE "Running unit tests failed 😞"
  exit $return_code
fi
