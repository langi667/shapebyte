#!/bin/bash
export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
cd "$SRCROOT/.."
./gradlew :shared:embedAndSignAppleFrameworkForXcode
