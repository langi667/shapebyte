#!/bin/bash
script_path=$(realpath "$0")
script_directory=$(dirname "$script_path")
cd "$script_directory/../" || exit 200

source "$script_directory/core/logging.sh"
source "$script_directory/core/error_codes.sh"
source "$script_directory/core/ios-settings.sh"

ios_project_dir="$script_directory/../iosApp"

compile() {
  local scheme=$1
  iOSLogS "📲 Compiling scheme $scheme ..."
  xcodebuild -project "$ios_project_dir/iosApp.xcodeproj" -scheme "$scheme" -configuration Debug -destination "platform=iOS Simulator,name=$IOS_SIM_DEVICE,OS=$IOS_SIM_OS"
  return_code=$?

  if [ $return_code -ne 0 ]; then
    iOSLogE "Target $scheme failed ☹️"
    # shellcheck disable=SC2086
    exit $ERROR_IOS_COMPILE_TARGET_FAILED
  else
    iOSLogS "Scheme $scheme successfully compiled! 😌"
  fi
}

compile "Development"
compile "Staging"
compile "Production"
