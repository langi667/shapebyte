GREEN='\033[0;32m'
RED='\033[0;31m'
ORANGE='\033[0;33m'
NC='\033[0m'

execute_snapshots() {
xcodebuild -project ./iosApp/iosApp.xcodeproj test -scheme Development -destination 'platform=iOS Simulator,name=iPhone 16,OS=18.0' -testPlan "SnapshotTests"
}
