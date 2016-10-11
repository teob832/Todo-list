#!/usr/local/bin/bash

echo "" >> build_log.log &&
date >> build_log.log &&
./gradlew assembleDebug >> build_log.log &&
adb -d install -r ./app/build/outputs/apk/app-debug.apk
