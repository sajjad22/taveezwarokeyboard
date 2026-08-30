# SindhiPheethoKeyboard (سنڌي ڦيٿو ڪيبورڊ) - Development & Setup Log

This document records the full history of the project, user requests, agent recommendations, environment setup details (what was installed on your PC, where, and why), design decisions for the 4-axis Sindhi Pheetho keyboard layout, letter frequency ranking, faded Shift keys, colored axis, custom app icon, and build instructions.

---

## 1. Project Overview & Request History

### User Request 1 (Initial Setup):
- User requested a **Sindhi (سنڌي)** version of the 8Vim gesture keyboard.
- Set up Android development tools on this laptop from scratch in user space.
- Fork 8Vim and generate the installable Android APK.

### User Request 2 (Layout Customization & Features):
- **Only 2 clean layouts**: English and Sindhi.
- **Clean 4-Axis Architecture (8 Spokes / 4 Levels = 32 Base + 32 Shift Slots)**:
  - 4 clean quadrants ($90^\circ$ Cartesian layout).
  - Perfectly stable circle resizing and letter positioning around the Xpad axes.
- **Letter Frequency Mapping (from `letter_frequency_new_only letters.txt`)**:
  - **Level 1 (Closest to circle, 8 spokes)**: Top 8 most frequent letters (`ي`, `ا`, `ن`, `و`, `ر`, `ھ`, `م`, `ج` = **61.6%** of all Sindhi text!).
  - **Level 2 (Ring 2, 8 spokes)**: Next 8 frequent letters (`س`, `ل`, `ت`, `ڪ`, `د`, `ب`, `پ`, `ئ` = **23.3%**).
  - **Level 3 (Ring 3, 8 spokes)**: Next 8 frequent letters (`ک`, `ڻ`, `ع`, `ٿ`, `ش`, `ڏ`, `ق`, `ح` = **7.8%**).
  - **Level 4 (Outermost ring, 8 spokes)**: Next 8 frequent letters (`ٽ`, `ف`, `چ`, `ڙ`, `گ`, `خ`, `ز`, `ص` = **4.8%**).
  - **Direct Base Layer Coverage: 97.5% of all Sindhi typing without Shift!**
- **Shift Layer (Low Frequency Letters + Airabs + Sacred Words)**:
  - Low-frequency letters: `ڌ`, `ڳ`, `ٻ`, `ط`, `ڊ`, `ٺ`, `ڀ`, `ڇ`, `ض`, `ظ`, `ڃ`, `غ`, `ڄ`, `ذ`, `ث`, `ڍ`, `ڦ`, `ڱ` (all 18 remaining letters!).
  - Diacritics / Airabs / حرڪتون: زبر (`َ`), زير (`ِ`), پيش (`ُ`), تشديد (`ّ`), جزم/سڪون (`ْ`), تنوين (`ً ٍ ٌ`), کڙي زبر (`ٰ`), کڙي زير (`ٖ`).
  - Sacred phrases & ligatures: `اللّٰه`, `محمّد`, `ﷺ`, `۽`, `آ`, `ء`.
  - Numerals: Sindhi numbers (`۰-۹`) and English numbers (`0-9`) on the numeric extra layer.
- **Faded Shift Key Letters on Wheel**:
  - Each key position displays its primary letter boldly AND displays its Shift letter with a subtle, faded opacity right next to it so you can always see what Shift types without memorization!
- **Colored Axis Lines**:
  - The 4 diagonal Xpad axis lines and circle feature a distinct primary accent color.
- **Live Letter Pop / Preview**:
  - When gliding over keys, the candidate letter pops up prominently on the bar above the keyboard in real time.
- **Custom App Icon**:
  - Modern vector icon depicting a glowing **Pheetho (Wheel) with keyboard keycaps and Sindhi center hub**.
- **App Name**:
  - **SindhiPheethoKeyboard** (سنڌي ڦيٿو ڪيبورڊ).

---

## 2. Tools & Components Installed on Your PC

All tools are installed in user space in `/home/sajjad/tools` (no root/sudo permissions needed):

| Component | Location | Version | Description / Purpose |
|---|---|---|---|
| **Git Repository** | `/home/sajjad/sindhivim` | v0.19.x (Sindhi Pheetho fork) | 8Vim codebase transformed into SindhiPheethoKeyboard. |
| **OpenJDK 17 (Temurin)** | `/home/sajjad/tools/jdk-17` | `17.0.20.1+1` | Java 17 Development Kit required by Gradle & Android Gradle Plugin. |
| **Android SDK Command-Line Tools** | `/home/sajjad/tools/android-sdk/cmdline-tools/latest` | `12.0` | `sdkmanager` used to download Android build tools and platform SDKs. |
| **Android SDK Platforms** | `/home/sajjad/tools/android-sdk/platforms/android-36`, `android-35` | API 35 & 36 | Android API target libraries. |
| **Android Build-Tools** | `/home/sajjad/tools/android-sdk/build-tools/36.0.0` | `36.0.0` | `aapt2`, `d8`, `zipalign`, and `apksigner` used to compile the APK. |
| **Local SDK Config** | `/home/sajjad/sindhivim/local.properties` | - | Points Gradle to `sdk.dir=/home/sajjad/tools/android-sdk`. |
| **Gradle Wrapper Cache** | `/home/sajjad/.gradle` | 9.4.0 | Gradle build cache and Kotlin compiler dependencies. |

---

## 3. 4-Axis Frequency Layout Specification (`sd.yaml`)

Each spoke contains 4 levels (Level 1 = closest to circle, Level 4 = outermost):

| Quadrant | Spoke | Level 1 (Rank 1-8) | Level 2 (Rank 9-16) | Level 3 (Rank 17-24) | Level 4 (Rank 25-32) |
|---|---|---|---|---|---|
| **Right ($0^\circ$)** | Top | **ي** (Shift: `ڌ`) | **س** (Shift: `ض`) | **ک** (Shift: `َ`) | **ٽ** (Shift: `ً`) |
| **Right ($0^\circ$)** | Bottom | **ا** (Shift: `ڳ`) | **ل** (Shift: `ظ`) | **ڻ** (Shift: `ِ`) | **ف** (Shift: `ٍ`) |
| **Top ($90^\circ$)** | Right | **ن** (Shift: `ٻ`) | **ت** (Shift: `ڃ`) | **ع** (Shift: `ُ`) | **چ** (Shift: `ٌ`) |
| **Top ($90^\circ$)** | Left | **و** (Shift: `ط`) | **ڪ** (Shift: `غ`) | **ٿ** (Shift: `ّ`) | **ڙ** (Shift: `ْ`) |
| **Left ($180^\circ$)** | Top | **ر** (Shift: `ڊ`) | **د** (Shift: `ڄ`) | **ش** (Shift: `ٰ`) | **گ** (Shift: `ڱ`) |
| **Left ($180^\circ$)** | Bottom | **ھ** (Shift: `ٺ`) | **ب** (Shift: `ذ`) | **ڏ** (Shift: `آ`) | **خ** (Shift: `ء`) |
| **Bottom ($270^\circ$)** | Left | **م** (Shift: `ڀ`) | **پ** (Shift: `ڦ`) | **ق** (Shift: `اللّٰه`) | **ز** (Shift: `محمّد`) |
| **Bottom ($270^\circ$)** | Right | **ج** (Shift: `ڇ`) | **ئ** (Shift: `ڍ`) | **ح** (Shift: `ﷺ`) | **ص** (Shift: `۽`) |

---

## 4. How to Rebuild and Install

### Rebuilding:
```bash
./build-apk.sh
```

### Ready APK:
👉 **[`/home/sajjad/sindhivim/SindhiPheethoKeyboard-debug.apk`](file:///home/sajjad/sindhivim/SindhiPheethoKeyboard-debug.apk)** *(Size: 22 MB)*

### Transfer to Phone:
```bash
python3 -m http.server 8080
```
Open `http://<laptop-ip>:8080/SindhiPheethoKeyboard-debug.apk` in phone browser and tap install.
