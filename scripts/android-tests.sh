

#!/bin/bash
script_path=$(realpath "$0")
script_directory=$(dirname "$script_path")
cd "$script_directory/../" || exit 200

source "$script_directory/core/logging.sh"
andLogI "Start first emulator found for Instrumentation tests"
emulator -list-avds | head -n 2 | xargs -I {} emulator -avd {} &
adb wait-for-device

andLogI "Start Android Instrumentation tests"
./gradlew connectedDevelopmentDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.emulator=true

return_code=$?
if [ $return_code -eq 0 ]; then
  andLogS "Running Android instrumented tests successful 😊"
else
  andLogE "Running Android instrumented tests failed 😞"
  andLogE "Make Sure to also have adb and emulator in your PATH. Both can be found in the Android SDK."
  exit $return_code
fi