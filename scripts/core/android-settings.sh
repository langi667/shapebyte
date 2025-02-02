script_path=$(realpath "$0")
script_directory=$(dirname "$script_path")
cd "$script_directory/../" || exit 200

source "$script_directory/core/logging.sh"

execute_snapshots() {
  ./gradlew validateDevelopmentDebugScreenshotTest
}

start_emulator() {
  adb shell getprop | grep -q "emulator"

  if [ $? -eq 0 ]; then
      andLogI "Emulator already running"
  else
     andLogI "Start first emulator found for Instrumentation tests"
     emulator -list-avds | head -n 2 | xargs -I {} emulator -avd {} &
     adb wait-for-device
  fi
}
