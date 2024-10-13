#!/bin/bash
script_path=$(realpath "$0")
script_directory=$(dirname "$script_path")
cd "$script_directory/../"

source $script_directory/core/logging.sh

lint() {
    ./gradlew shared:detekt
}

lint_check() {
    sharedLogI "📋 Starting lint checks ..."

    lint_run_count=0
    lint
    return_code=$?

    # Check the return code
    if [ $return_code -ne 0 ]; then
      sharedLogW "Lint check failed on the first run, retrying"
      lint_run_count=1
      lint
      return_code=$?
    fi

    if [ $lint_run_count -eq 0 ] && [ $return_code -eq 0 ]; then
        sharedLogS "Lint checks successful 🙂"
        return 0
    elif [ $lint_run_count -ne 0 ] && [ $return_code -eq 0 ]; then
        sharedLogW  "Lint check was partially successful, check the code adjustments and commit"
        return 1
    else
        sharedLogE "Lint check failed"
        return 2
    fi
}

lint_check
