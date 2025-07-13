#!/bin/bash
script_path=$(realpath "$0")
script_directory=$(dirname "$script_path")
cd "$script_directory/../" || exit 200

source "$script_directory/core/logging.sh"
source "$script_directory/core/android-settings.sh"

sharedLogI "👩‍🔬 Start unit tests ..."
start_emulator
adb uninstall "de.stefan.lang.shapebyte.android.dev"
./gradlew :core:allTestDebugUnitTest :core:connectedAndroidTest :core:iosSimulatorArm64Test -Pandroid.testInstrumentationRunnerArguments.emulator=true
./gradlew :foundation:allTestDebugUnitTest :foundation:connectedAndroidTest :foundation:iosSimulatorArm64Test -Pandroid.testInstrumentationRunnerArguments.emulator=true
./gradlew :designsystem:allTestDebugUnitTest :designsystem:connectedAndroidTest :designsystem:iosSimulatorArm64Test -Pandroid.testInstrumentationRunnerArguments.emulator=true
./gradlew :shared:allTestDebugUnitTest :shared:connectedAndroidTest :shared:iosSimulatorArm64Test -Pandroid.testInstrumentationRunnerArguments.emulator=true

return_code=$?
if [ $return_code -eq 0 ]; then
  sharedLogS "Running unit tests successful 😊"
else
  sharedLogE "Running unit tests failed 😞"
  exit $return_code
fi
