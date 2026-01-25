GREEN='\033[0;32m'
RED='\033[0;31m'
ORANGE='\033[0;33m'
NC='\033[0m'

IOS_SIM_DEVICE='iPhone 16 Pro'
IOS_SIM_OS='26.0'

execute_snapshots() {
xcodebuild -project ./iosApp/iosApp.xcodeproj test -scheme Development -destination "platform=iOS Simulator,name=$IOS_SIM_DEVICE,OS=$IOS_SIM_OS" -testPlan "SnapshotTests"
}
