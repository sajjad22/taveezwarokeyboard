# SindhiPheethoKeyboard (سنڌي ڦيٿو ڪيبورڊ) - Development & Setup Log

This document records the full history of the project, user requests, agent recommendations, environment setup details (what was installed on your PC, where, and why), design decisions for the 4-axis Sindhi Pheetho keyboard layout, letter frequency ranking, faded Shift keys, colored axis, custom app icon, UI translations, and build instructions.

---

## 1. Project Overview & Request History

### User Requests & Summary of Features:

1. **Complete Sindhi User Interface (سنڌي ترجمو)**:
   - Full localization in authentic Sindhi across settings, dialogs, and setup screens.
2. **Bottom Padding & Wheel Elevation**:
   - Keyboard wheel lifted with a bottom margin so bottom letters are fully visible above Android navigation gestures/bars.
3. **Lateral Letter Spacing from Diagonal Axes**:
   - Increased lateral spoke offset (`characterHeight * 0.88f`) so letters never touch or overlap the diagonal axis lines.
4. **Direct Layout Picker in Menu**:
   - Clicking "Layouts / لئـائوٽس" in settings directly opens the selection dialog.
5. **Sidebar Icons & Keypad Restructuring**:
   - Replaced Emoji button with **Language Switcher (🌐)** (toggles between Sindhi and English instantly).
   - Replaced Tab button with **Keyboard Switcher (⌨️)** (opens system Input Method picker).
   - Removed Paste Pad (clipboard) button.
   - Moved **Settings Gear (⚙️)** button to the bottom of the sidebar.
6. **Responsive Circle Sizing**:
   - Circle radius factor formula now scales smoothly and dynamically when adjusting the circle size slider.
7. **Prebuilt Themes (ٿيم ۽ رنگ)**:
   - **Sindhi Ajrak (سنڌي اجرڪ)**: Traditional Maroon (`#1E0E14`), Crimson (`#E11D48`), and Soft Rose.
   - **Emerald Night (زمرد سائو)**: Deep Forest Green (`#06281E`) and Mint Accent (`#10B981`).
   - **Royal Indigo (شاهي نيرو)**: Deep Navy (`#0F172A`), Sky Blue (`#38BDF8`), and Gold.
   - **Midnight AMOLED (نيم رات ڪارو)**: Pitch Black (`#000000`) and Neon Cyan (`#00E5FF`).
   - **Sunset Sand (ٿر جو وارياسو)**: Warm Amber, Terracotta, and Desert Sand.
   - **Cyberpunk Neon (نيون روشني)**: Deep Purple and Electric Pink/Cyan.
   - Standard Light, Dark, and Custom color themes.
8. **Basic Tutorial / رهنمائي (`TutorialScreen.kt`)**:
   - Replaced the external Help link with a built-in step-by-step tutorial in Sindhi explaining the Pheetho wheel gesture typing system.
9. **About Screen & Attribution**:
   - "Sindhi Pheetho Keyboard was forked from 8Vim (which was inspired by the 8Pen concept) and redesigned & developed for the Sindhi language by Sajjad Ali."
10. **Developer Social Link**:
    - Direct link to `https://x.com/makorro`.

---

## 2. Format for Word Suggestion / Prediction Data

You can provide your Sindhi suggestion dataset in standard **UTF-8 CSV or TSV format**:

### Format (`word,frequency`):
```csv
آھي,120000
۾,98000
جي,85000
کي,79000
ته,64000
ڪري,58000
سنڌ,52000
پاڪستان,49000
سان,45000
ٿيو,41000
```
- **Column 1**: Sindhi word (e.g. `سنڌ`)
- **Column 2**: Frequency count / integer weight (e.g. `52000`)
- **Location**: Place the file in the repository root or provide the file, and we will place it into `8vim/src/main/assets/word_seed.csv`.

---

## 3. 4-Axis Frequency Layout Specification (`sd.yaml`)

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
Open `http://<laptop-ip>:8080/SindhiPheethoKeyboard-debug.apk` on your phone browser and tap install.
