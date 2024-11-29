#!/bin/bash
if [[ "$(uname -m)" == arm64 ]]; then
    export PATH="/opt/homebrew/bin:$PATH"
fi

script_path=$(realpath "$0")
script_directory=$(dirname "$script_path")

source "$script_directory/core/logging.sh"

lint() {
     swiftlint "$script_directory/../iosApp"
}

iOSLogI "📋 Starting lint checks ..."
lint
return_code=$?

if [ $return_code -ne 0 ]; then
  iOSLogE "Lint check failed 😣"
else
  iOSLogS "Lint check successful! 😀"
fi
exit $return_code
