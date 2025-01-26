#!/bin/bash
script_path=$(realpath "$0")
script_directory=$(dirname "$script_path")
cd "$script_directory/../" || exit 200

source "$script_directory/core/logging.sh"
source "$script_directory/core/android-settings.sh"
start_emulator

andLogI "Start Android Instrumentation tests"
./gradlew connectedDevelopmentDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.emulator=true

return_code=$?
if [ $return_code -eq 0 ]; then
  andLogS "Running Android instrumented tests successful 😊"
else
  andLogE "Running Android instrumented tests failed 😞"
  andLogE "Make Sure to also have adb and emulator in your PATH. Both can be found in the Android SDK or at ~/Library/Android/sdk/platform-tools/ and ~/Library/Android/sdk/emulator"
  exit $return_code
fi