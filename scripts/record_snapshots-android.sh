#!/bin/bash
script_path=$(realpath "$0")
script_directory=$(dirname "$script_path")
cd "$script_directory/../" || exit 200

source "$script_directory/core/logging.sh"
source "$script_directory/core/error_codes.sh"
source "$script_directory/core/android-settings.sh"

andLogI "🗑️ Clear current snapshot folder ..."
"$script_directory/clear_snapshots-android.sh"

andLogI "📸 Recording snapshots (May take a while! ☕️ - time)..."
./gradlew updateDevelopmentDebugScreenshotTest > /dev/null 2>&1

andLogI "📸 Verifying snapshots..."
execute_snapshots > /dev/null 2>&1

return_code=$?
if [ $return_code -eq 0 ]; then
  andLogS "Recording & verifying snapshots successful 😊"
else
  andLogE "Recording & verifying failed 😞"
  exit $return_code
fi





