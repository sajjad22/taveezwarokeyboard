# SindhiPheethoKeyboard (سنڌي ڦيٿو ڪيبورڊ) - Development & Setup Log

This document records the full history of the project, user requests, agent recommendations, environment setup details (what was installed on your PC, where, and why), design decisions for the 5-axis Sindhi Pheetho keyboard layout, live gesture preview popup, and build instructions.

---

## 1. Project Overview & Request History

### User Request 1 (Initial):
- User requested a **Sindhi (سنڌي)** version of the 8Vim gesture keyboard.
- Set up Android development tools on this laptop from scratch in user space.
- Fork 8Vim and generate the installable Android APK.

### User Request 2 (5-Axis Wheel + Frequency Ranking + Live Letter Pop + App Renaming):
- **Only 2 layouts**: English and Sindhi (remove all other 34 default languages).
- **5-Axis Engine (50 keys on 1 single layer)**:
  - 5 axes $\times$ 2 spokes per axis = 10 spokes.
  - 5 letters per spoke (Levels 1 to 5) = exactly **50 direct single-layer letter keys**.
  - `جه` and `گه` managed as `ج+ھ` and `گ+ھ`, leaving exactly 50 single letters in Sindhi.
- **Letter Frequency Mapping**:
  - Letters placed on the wheel based on frequency (`letter_frequency_new_only letters.txt`).
  - Most frequent letters (ي, ا, ن, و, ر, ھ, م, ج, س, ل) are on **Level 1 (nearest to the center circle)** for instant 1-step swipe speed.
- **Shift & Extra Layer**:
  - Diacritics / Airabs / حرڪتون: زبر (`َ`), زير (`ِ`), پيش (`ُ`), تشديد (`ّ`), جزم/سڪون (`ْ`), تنوين (`ً ٍ ٌ`), کڙي زبر (`ٰ`), کڙي زير (`ٖ`), مد.
  - Sacred phrases / words: `اللّٰه`, `محمّد`, `ﷺ`, `۽` (Sindhi "and" ligature).
  - Numerals: Sindhi numbers (`۰-۹`) and English numbers (`0-9`).
- **Live Letter Pop / Preview**:
  - When gliding/swiping across sectors, the candidate letter pops up prominently on the bar above the keyboard in real-time before releasing.
- **New App Name**:
  - **SindhiPheethoKeyboard** (سنڌي ڦيٿو ڪيبورڊ).

---

## 2. Tools & Components Installed on Your PC

All tools are installed in user-space inside `/home/sajjad/tools` (no root/sudo permissions needed):

| Component | Location | Version | Description / Purpose |
|---|---|---|---|
| **Git Repository** | `/home/sajjad/sindhivim` | v0.19.x (5-axis fork) | 8Vim codebase transformed into the 5-axis SindhiPheethoKeyboard. |
| **OpenJDK 17 (Temurin)** | `/home/sajjad/tools/jdk-17` | `17.0.20.1+1` | Java 17 Development Kit required by Gradle & Android Gradle Plugin. |
| **Android SDK Command-Line Tools** | `/home/sajjad/tools/android-sdk/cmdline-tools/latest` | `12.0` | `sdkmanager` used to download Android build tools and platform SDKs. |
| **Android SDK Platforms** | `/home/sajjad/tools/android-sdk/platforms/android-36`, `android-35` | API 35 & 36 | Android API target libraries. |
| **Android Build-Tools** | `/home/sajjad/tools/android-sdk/build-tools/36.0.0` | `36.0.0` | `aapt2`, `d8`, `zipalign`, and `apksigner` used to compile the APK. |
| **Local SDK Config** | `/home/sajjad/sindhivim/local.properties` | - | Points Gradle to `sdk.dir=/home/sajjad/tools/android-sdk`. |
| **Gradle Wrapper Cache** | `/home/sajjad/.gradle` | 9.4.0 | Gradle build cache and Kotlin compiler dependencies. |

---

## 3. 5-Axis (50-Key) Frequency Layout Specification

### A. Sector Angles & Geometry
- **Sector 0 (`right`)**: $0^\circ$ (East)
- **Sector 1 (`top_right`)**: $72^\circ$ (North-East)
- **Sector 2 (`top_left`)**: $144^\circ$ (North-West)
- **Sector 3 (`bottom_left`)**: $216^\circ$ (South-West)
- **Sector 4 (`bottom_right`)**: $288^\circ$ (South-East)
- Dividing radial lines at: $36^\circ, 108^\circ, 180^\circ, 252^\circ, 324^\circ$.

### B. Sindhi 50-Key Single Layer Layout (`sd.yaml`)

Each spoke contains 5 levels (Level 1 = closest to circle, Level 5 = outermost):

| Sector | Spoke | Level 1 (Rank 1-10) | Level 2 (Rank 11-20) | Level 3 (Rank 21-30) | Level 4 (Rank 31-40) | Level 5 (Rank 41-50) |
|---|---|---|---|---|---|---|
| **Right ($0^\circ$)** | Top | **ي** (Shift: َ) | **ت** (Shift: ٽ) | **ش** (Shift: ِ) | **ز** (Shift: ُ) | **ض** (Shift: ّ) |
| **Right ($0^\circ$)** | Bottom | **ا** (Shift: آ) | **ڪ** (Shift: ک) | **ڏ** (Shift: ْ) | **ص** (Shift: ً) | **ظ** (Shift: ٍ) |
| **Top-Right ($72^\circ$)** | Top | **ن** (Shift: ڻ) | **د** (Shift: ڌ) | **ق** (Shift: ء) | **ڌ** (Shift: ٰ) | **ڃ** (Shift: ٌ) |
| **Top-Right ($72^\circ$)** | Bottom | **و** (Shift: ؤ) | **ب** (Shift: ٻ) | **ح** (Shift: خ) | **ڳ** (Shift: ڱ) | **غ** (Shift: ـ) |
| **Top-Left ($144^\circ$)** | Top | **ر** (Shift: ڙ) | **پ** (Shift: ڀ) | **ٽ** (Shift: ٺ) | **ٻ** (Shift: ؟) | **ڄ** (Shift: ،) |
| **Top-Left ($144^\circ$)** | Bottom | **ھ** (Shift: ه) | **ئ** (Shift: ى) | **ف** (Shift: ڦ) | **ط** (Shift: ؛) | **ذ** (Shift: ژ) |
| **Bottom-Left ($216^\circ$)** | Top | **م** (Shift: محمّد) | **ک** (Shift: ڪ) | **چ** (Shift: ڇ) | **ڊ** (Shift: ڍ) | **ث** (Shift: !) |
| **Bottom-Left ($216^\circ$)** | Bottom | **ج** (Shift: ڄ) | **ڻ** (Shift: ن) | **ڙ** (Shift: ر) | **ٺ** (Shift: .) | **ڍ** (Shift: -) |
| **Bottom-Right ($288^\circ$)** | Top | **س** (Shift: ش) | **ع** (Shift: غ) | **گ** (Shift: ڳ) | **ڀ** (Shift: ۽) | **ڦ** (Shift: «) |
| **Bottom-Right ($288^\circ$)** | Bottom | **ل** (Shift: اللّٰه) | **ٿ** (Shift: ٺ) | **خ** (Shift: ح) | **ڇ** (Shift: ﷺ) | **ڱ** (Shift: ») |

---

## 4. Live Letter Pop / Preview Feature

- As you glide your finger across the sectors on the wheel, `KeyboardController` detects the candidate letter and updates `keyboardManager.previewChar`.
- The top suggestion bar ([`SuggestionsBar.kt`](file:///home/sajjad/sindhivim/8vim/src/main/kotlin/inc/flide/vim8/ime/keyboard/view/SuggestionsBar.kt)) displays a prominent, centered popup badge with the hovered letter in real-time.
- When you release your finger, the character is typed and the bar smoothly returns to showing regular text/word suggestions.

---

## 5. APK Build & Installation

### Rebuilding Anytime:
```bash
./build-apk.sh
```

### Ready APK Location:
👉 **[`/home/sajjad/sindhivim/SindhiPheethoKeyboard-debug.apk`](file:///home/sajjad/sindhivim/SindhiPheethoKeyboard-debug.apk)** *(Size: 22 MB)*

### Quick Transfer to Phone:
```bash
python3 -m http.server 8080
# Open on phone browser: http://<laptop-ip>:8080/SindhiPheethoKeyboard-debug.apk
```
