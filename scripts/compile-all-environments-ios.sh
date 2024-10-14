#!/bin/bash
script_path=$(realpath "$0")
script_directory=$(dirname "$script_path")
cd "$script_directory/../" || exit 200

source "$script_directory/core/logging.sh"
source "$script_directory/core/error_codes.sh"

ios_project_dir="$script_directory/../iosApp"

compile() {
  local target=$1
  iOSLogS "📲 Compiling target $target ..."
  xcodebuild -project "$ios_project_dir/iosApp.xcodeproj" -target "Development" -configuration Debug
  return_code=$?

  if [ $return_code -ne 0 ]; then
    iOSLogE "Target $target failed ☹️"
    # shellcheck disable=SC2086
    exit $ERROR_ANDROID_COMPILE_TARGET_FAILED
  else
    iOSLogS "Target $target successfully compiled! 😌"
  fi
}

compile "Development"
compile "Staging"
compile "Production"