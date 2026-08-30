# SindhiVim (8Vim in Sindhi) - Development & Setup Log

This document records the full history of the project, user requests, agent recommendations, environment setup details (what was installed on your PC, where, and why), design decisions for the Sindhi keyboard layout, and build instructions.

---

## 1. Project Overview & Request History

### User Request (Initial):
- User loves the **8Vim** gesture keyboard ([8VIM GitHub Repo](https://github.com/8VIM/8VIM.git)).
- Wants to create a **Sindhi (سنڌي)** version of the 8Vim keyboard.
- Asked for fork/setup on this PC, creation of the installable Android APK, suggestions before proceeding, and noted that they are new to Android development with a fresh laptop lacking development tools.

### Agent Recommendations & Analysis:
- **8Vim Layout Architecture**: 8Vim uses YAML-based sector configurations (`res/raw/<lang>.yaml`). 4 sectors (Right, Top, Left, Bottom), each with 2 spokes (total 8 spokes), each spoke with 4 gesture levels (32 slots on the default layer).
- **Sindhi Alphabet Support**: Perso-Arabic Sindhi has 52 letters. By pairing primary letters in `lower_case` with dotted/aspirated variants in `upper_case` (Shift) across the 32 slots, 64 characters are accessible directly on the primary wheel.
- **Extra Layer**: Diacritics (حرڪتون: زبر، زير، پيش، تشديد، وغيره), Sindhi numerals (۰-۹), and Sindhi punctuation (؟ ، ؛ ۽).
- **Automated Local Setup**: Set up OpenJDK 17 and Android SDK Command-Line Tools in user space (`~/tools/`) without requiring root/sudo privileges.

### User Request (Follow-up):
- Proceed with setting up the build environment, Sindhi layout implementation, and keep an active log of all actions, questions, answers, and installed tools in this file.

---

## 2. Tools & Components Installed on Your PC

All tools are installed in user-space inside `/home/sajjad/tools` so they do not pollute system packages or require administrative (sudo) privileges.

| Component | Location | Version | Description / Purpose |
|---|---|---|---|
| **Git Repository** | `/home/sajjad/sindhivim` | v0.19.x (main) | The 8Vim codebase cloned and modified for Sindhi support. |
| **OpenJDK 17 (Temurin)** | `/home/sajjad/tools/jdk-17` | `17.0.20.1+1` | Java 17 Development Kit required by Gradle & Android Gradle Plugin 9.x. |
| **Android SDK Command-Line Tools** | `/home/sajjad/tools/android-sdk/cmdline-tools/latest` | `12.0` | `sdkmanager` used to download Android build tools and platform SDKs. |
| **Android SDK Platforms** | `/home/sajjad/tools/android-sdk/platforms/android-36`, `android-35` | API 35 & 36 | Android API level targets required for compilation. |
| **Android Build-Tools** | `/home/sajjad/tools/android-sdk/build-tools/36.0.0` | `36.0.0` | `aapt2`, `d8`, `zipalign`, and `apksigner` used to compile and package the APK. |
| **Local SDK Config** | `/home/sajjad/sindhivim/local.properties` | - | Tells Gradle where Android SDK resides (`sdk.dir=/home/sajjad/tools/android-sdk`). |
| **Gradle Wrapper Cache** | `/home/sajjad/.gradle` | 9.4.0 | Gradle build engine and downloaded build dependencies. |

---

## 3. Keyboard Layout Design Specification (`sd.yaml`)

- **ISO Language Code**: `sd` (Sindhi / سنڌي)
- **File Location**: [`8vim/src/main/res/raw/sd.yaml`](file:///home/sajjad/sindhivim/8vim/src/main/res/raw/sd.yaml)
- **Default Layer Sectors**:
  - **Right Sector**:
    - *Bottom Spoke*: و (Shift: ؤ), ر (Shift: ڙ), ح (Shift: خ), ؟ (Shift: !)
    - *Top Spoke*: ا (Shift: آ), ع (Shift: غ), ج (Shift: ڄ), چ (Shift: ڇ)
  - **Top Sector**:
    - *Right Spoke*: ه (Shift: ھ), ف (Shift: ڦ), ص (Shift: ض), ط (Shift: ظ)
    - *Left Spoke*: م (Shift: ء), ق, ذ (Shift: ژ), ' (Shift: ")
  - **Left Sector**:
    - *Top Spoke*: ن (Shift: ڻ), د (Shift: ڌ), ث (Shift: ڃ), . (Shift: -)
    - *Bottom Spoke*: ي (Shift: ئ), ت (Shift: ٽ), ٿ (Shift: ٺ), ، (Shift: _)
  - **Bottom Sector**:
    - *Left Spoke*: ل (Shift: ۽), س (Shift: ش), ڊ (Shift: ڍ), ڏ (Shift: ز)
    - *Right Spoke*: ب (Shift: ٻ), پ (Shift: ڀ), ڪ (Shift: ک), گ (Shift: ڳ)
- **Extra Layer (Numbers & Diacritics)**:
  - Sindhi Digits: `۰`, `۱`, `۲`, `۳`, `۴`, `۵`, `۶`, `۷`, `۸`, `۹`
  - Diacritics: `َ` (Fatha/Zabar), `ِ` (Kasra/Zer), `ُ` (Damma/Pesh), `ّ` (Shadda/Tashdeed), `ْ` (Sukun/Jazm), `ً` (Tanween Fath), `ٍ` (Tanween Kasr), `ٰ` (Superscript Alif / Khari Zabar)
  - Extra letters & Ligatures: `ڱ`, `ء`, `ى`, `۽` (Sindhi "and"), `«`, `»`, `؛`

---

## 4. How to Build, Rebuild, and Customize

### One-Click Build Script:
We created a helper script [`build-apk.sh`](file:///home/sajjad/sindhivim/build-apk.sh) in the root directory. To rebuild the APK anytime:
```bash
./build-apk.sh
```

### Generated APK Location:
- **File**: [`/home/sajjad/sindhivim/SindhiVim-debug.apk`](file:///home/sajjad/sindhivim/SindhiVim-debug.apk)
- **Size**: ~21 MB

---

## 5. How to Install on Your Android Phone

1. **Transfer the APK to your phone**:
   - Via USB cable, Bluetooth, Google Drive, WhatsApp/Telegram saved messages, or local HTTP server:
     ```bash
     # Quick local HTTP server from your laptop:
     python3 -m http.server 8080
     # On your phone browser connected to the same Wi-Fi, open: http://<laptop-ip>:8080/SindhiVim-debug.apk
     ```
2. **Install the APK**:
   - Tap on `SindhiVim-debug.apk` on your Android phone and select **Install** (allow "Install unknown apps" if prompted).
3. **Enable SindhiVim Keyboard**:
   - Open **Settings** -> **System** -> **Languages & Input** -> **On-screen keyboard** -> **Manage on-screen keyboards**.
   - Turn ON **8Vim**.
4. **Select Sindhi Layout**:
   - Open 8Vim settings app -> **Layout** -> Select **سنڌي (Sindhi)**.
