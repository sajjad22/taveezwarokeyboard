#!/usr/bin/env bash
set -e

export ANDROID_HOME="${ANDROID_HOME:-$HOME/tools/android-sdk}"
export PATH="$ANDROID_HOME/platform-tools:$PATH"

OUTPUT_FILE="crash_log.txt"

echo "========================================================"
echo "   Taveez Waro Keyboard - Crash Log Capture Tool"
echo "========================================================"

if ! command -v adb &> /dev/null; then
    echo "❌ Error: 'adb' command not found."
    echo "Please ensure Android SDK platform-tools are installed."
    exit 1
fi

DEVICE_COUNT=$(adb devices | grep -v "List of devices" | grep -v "^$" | wc -l)

if [ "$DEVICE_COUNT" -eq 0 ]; then
    echo "⚠️  No Android device detected over ADB."
    echo ""
    echo "To connect your device:"
    echo "  1. Enable 'Developer Options' on your Android device (tap 'Build number' 7 times in Settings > About phone)."
    echo "  2. Turn on 'USB Debugging' in Developer options."
    echo "  3. Connect phone via USB cable and tap 'Allow USB debugging' when prompted on phone."
    echo ""
    echo "Waiting for device to connect... (Press Ctrl+C to cancel)"
    adb wait-for-device
fi

echo "✅ Device connected!"
echo "Capturing recent crash logs for Taveez Waro Keyboard..."
echo ""

# Extract recent crash logs
adb logcat -d -v time \
    AndroidRuntime:E \
    DEBUG:E \
    FATAL:E \
    *:F \
    | grep -E "FATAL EXCEPTION|com.sajjad.taveezwarokeyboard|inc.flide.vim8|MainActivity|AndroidRuntime|Caused by:" -B 2 -A 35 > "$OUTPUT_FILE" || true

# If empty, capture full crash buffer
if [ ! -s "$OUTPUT_FILE" ]; then
    adb logcat -b crash -d -v time > "$OUTPUT_FILE" || true
fi

# If still small or empty, grab last 200 lines of general error logs
if [ ! -s "$OUTPUT_FILE" ]; then
    adb logcat -d -v time *:E | tail -n 200 > "$OUTPUT_FILE" || true
fi

echo "========================================================"
echo "               CRASH LOG OUTPUT                         "
echo "========================================================"
cat "$OUTPUT_FILE"
echo "========================================================"
echo "✅ Log saved to: $(pwd)/$OUTPUT_FILE"
echo "You can copy and paste the text above directly into our chat!"
echo "========================================================"
