#!/bin/bash
script_path=$(realpath "$0")
script_directory=$(dirname "$script_path")
cd "$script_directory/../" || exit 200

source "$script_directory/core/logging.sh"
source "$script_directory/core/error_codes.sh"
source "$script_directory/core/ios-settings.sh"

iOSLogI "🗑️ Clear current snapshot folder ..."
"$script_directory/clear-snapshots-ios.sh"

iOSLogI "📸 Recording snapshots (May take a while! ☕️ - time)..."
execute_snapshots > /dev/null 2>&1

iOSLogI "📸 Verifying snapshots..."
execute_snapshots

return_code=$?
if [ $return_code -eq 0 ]; then
  iOSLogS "Recording snapshots successful 😊"
else
  iOSLogE "Recording snapshots failed, most likely there was an error during verification 😞"
  exit $return_code
fi
