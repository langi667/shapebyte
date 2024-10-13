#!/bin/bash
script_path=$(realpath "$0")
script_directory=$(dirname "$script_path")
project_root="$script_directory/../"
cd "$project_root"

source $script_directory/core/logging.sh

lint() {
     DIR=$PWD
     param=$1
     cd "$project_root/iosApp"
     swiftlint $param
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