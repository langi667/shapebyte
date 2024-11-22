#!/bin/bash
script_path=$(realpath "$0")
script_directory=$(dirname "$script_path")
cd "$script_directory/../" || exit 200

source "$script_directory/core/logging.sh"
source "$script_directory/core/error_codes.sh"

# Function to recursively search and delete __Snapshots__ directories
delete_snapshots() {
    for dir in "$1"/*; do
        if [ -d "$dir" ]; then
            if [ "$(basename "$dir")" == "__Snapshots__" ]; then
                echo "Deleting directory: $dir"
                rm -rf "$dir"
            else
                delete_snapshots "$dir"
            fi
        fi
    done
}

# Start the recursive deletion from the specified directory
start_dir="iosApp/iosApp"
if [ -z "$start_dir" ]; then
    echo "Usage: $0 <start_directory>"
    exit 1
fi

delete_snapshots "$start_dir"