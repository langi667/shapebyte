#!/bin/bash
script_path=$(realpath "$0")
script_directory=$(dirname "$script_path")
cd "$script_directory/../" || exit 200

source "$script_directory/core/logging.sh"
source "$script_directory/core/ios-settings.sh"

iOSLogI "📸 Start snapshot tests ..."
execute_snapshots

return_code=$?
if [ $return_code -eq 0 ]; then
  iOSLogS "Running snapshot tests successful 😊"
else
  iOSLogE "Running snapshot tests failed 😞"
  exit $return_code
fi
