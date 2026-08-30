#!/bin/bash
set -e

# Export local user-space JDK and Android SDK paths
export JAVA_HOME=/home/sajjad/tools/jdk-17
export ANDROID_HOME=/home/sajjad/tools/android-sdk
export PATH=$JAVA_HOME/bin:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools:$PATH

echo "=== Building SindhiPheethoKeyboard APK ==="
./gradlew assembleDebug

cp 8vim/build/outputs/apk/debug/8vim-debug.apk ./SindhiPheethoKeyboard-debug.apk

echo "=== Build Complete! ==="
echo "APK Location: $(pwd)/SindhiPheethoKeyboard-debug.apk"
ls -lh ./SindhiPheethoKeyboard-debug.apk
