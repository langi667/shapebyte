GREEN='\033[0;32m'
RED='\033[0;31m'
ORANGE='\033[0;33m'
NC='\033[0m'

PLATFORM_ANDROID="ANDROID"
PLATFORM_IOS="iOS"
PLATFORM_SHARED="SHARED"

log() {
    local platform=$1
    local msg=$2
    echo -e "${NC}[$1] $msg ${NC}"
}

logS() {
    BOLD=$(tput bold)
    NORMAL=$(tput sgr0)

    local platform=$1
    local msg=$2
    echo -e "${GREEN}${BOLD}[$1] $msg ${NC}${NORMAL}"
}

logI() {
    local platform=$1
    local msg=$2
    echo -e "${GREEN}[$1] $msg ${NC}"
}

logW() {
    local platform=$1
    local msg=$2
    echo -e "${ORANGE}[$1] $msg ${NC}"
}

logE() {
    local platform=$1
    local msg=$2
    echo -e "${RED}[$1] $msg ${NC}"
}

andLog() {
    local msg=$1
    log "$PLATFORM_ANDROID" "$msg"
}

andLogI() {
    local msg=$1
    logI "$PLATFORM_ANDROID" "$msg"
}

andLogS() {
    local msg=$1
    logS "$PLATFORM_ANDROID" "$msg"
}

andLogW() {
    local msg=$1
    logW "$PLATFORM_ANDROID" "$msg"
}

andLogE() {
    local msg=$1
    logE "$PLATFORM_ANDROID" "$msg"
}

iOSLog() {
    local msg=$1
    log "$PLATFORM_IOS" "$msg"
}

iOSLogI() {
    local msg=$1
    logI "$PLATFORM_IOS" "$msg"
}

iOSLogS() {
    local msg=$1
    logI "$PLATFORM_IOS" "$msg"
}

iOSLogW() {
    local msg=$1
    logW "$PLATFORM_IOS" "$msg"
}

iOSLogE() {
    local msg=$1
    logE "$PLATFORM_IOS" "$msg"
}

sharedLog() {
    local msg=$1
    log "$PLATFORM_SHARED" "$msg"
}

sharedLogI() {
    local msg=$1
    logI "$PLATFORM_SHARED" "$msg"
}

sharedLogS() {
    local msg=$1
    logS "$PLATFORM_SHARED" "$msg"
}

sharedLogW() {
    local msg=$1
    logW "$PLATFORM_SHARED" "$msg"
}

sharedLogE() {
    local msg=$1
    logE "$PLATFORM_SHARED" "$msg"
}
