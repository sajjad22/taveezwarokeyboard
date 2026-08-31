# SindhiPheethoKeyboard (سنڌي ڦيٿو ڪيبورڊ) - Development & Setup Log

This document records the full history of the project, user requests, agent recommendations, environment setup details (what was installed on your PC, where, and why), design decisions for the 4-axis Sindhi Pheetho keyboard layout, letter frequency ranking, faded Shift keys, colored axis, custom app icon, UI translations, and build instructions.

---

## 1. Project Overview & Request History

### User Requests & Summary of Features:

1. **Dual Language Prediction Engine (English & Sindhi)**:
   - English word predictions on English layout (`assets/word_seed_en.csv`).
   - Sindhi word predictions on Sindhi layout (`assets/word_seed_sd.csv` with 17,863 high-frequency words).
   - Intelligent script detection dynamically separates English and Sindhi learning.
2. **First Layer (Main Pheetho Wheel) Enhancements**:
   - Standard Sindhi Heh (`ه`) placed at Level 1 (replacing `ھ`).
   - `آ` (Alif-Madda) and `۽` (Sindhi "and" conjunction) brought to the main layer.
   - `۾` placed in Level 1 (Bottom Left).
3. **Second Layer (Shift Pheetho Wheel) Reorganization**:
   - **Sacred Names at Level 1 (Closest to Circle)**: `اللّٰه`, `محمّد`, `ﷺ`.
   - High-utility Sindhi letters and Heh forms (`ھ`, `ۂ`, `ۃ`, `ٻ`, `ڀ`, `ڌ`, `ٺ`).
   - Secondary Sindhi & Urdu letters (`ض`, `ظ`, `ڃ`, `ط`, `ڄ`, `ذ`, `ڦ`, `ڇ`, `ڳ`, `غ`, `ڱ`, `ڍ`, `ء`, `ژ`, `ص`, `ف`, `خ`, `ث`).
4. **Numeric & Airabs Keypad (`NumberLayout.kt`)**:
   - Side columns dedicated to frequent airabs: `َ` (Zabar), `ِ` (Zer), `ُ` (Pesh), `ّ` (Tashdeed), `ْ` (Jazam), `ً` (Do Zabar), `ٍ` (Do Zer), `ٌ` (Do Pesh), `ٰ` (Khari Zabar), `ـ` (Kashida), `ء` (Hamza).
   - Full stop (`.`), Sindhi comma (`،`), question mark (`؟`), digits `0-9`, and highlighted Pheetho wheel return button.
5. **Double-Tap Circle for Full Stop**:
   - Double-tapping inside the center circle inserts a full stop (`.`).
   - Configurable from **اشارا (Gestures)** settings: "مرڪز تي ڊبل ٽيپ سان فل اسٽاپ".
6. **Bottom Padding & Navigation Bar Clearance**:
   - Elevated keyboard layout by 24dp so the settings gear and sidebar are clear of Android's bottom navigation and minimize bar.
7. **Pure Sindhi UI**:
   - Concise noun/action terms throughout settings and setup wizard.
8. **About Page (ڪيبورڊ بابت)**:
   - Title: **ڪيبورڊ بابت**
   - Description: **Inspired by 8Pen concept.**

---

## 2. Updated 4-Axis Frequency Layout (`sd.yaml`)

| Quadrant | Spoke | Level 1 (Inner) | Level 2 | Level 3 | Level 4 (Outer) |
|---|---|---|---|---|---|
| **Right ($0^\circ$)** | Top | **ي** (Shift: `اللّٰه`) | **س** (Shift: `ض`) | **ک** (Shift: `ڳ`) | **ٽ** (Shift: `ص`) |
| **Right ($0^\circ$)** | Bottom | **ا** (Shift: `محمّد`) | **ل** (Shift: `ظ`) | **ڻ** (Shift: `غ`) | **۽** (Shift: `ف`) |
| **Top ($90^\circ$)** | Right | **ن** (Shift: `ﷺ`) | **ت** (Shift: `ڃ`) | **ع** (Shift: `ڱ`) | **چ** (Shift: `خ`) |
| **Top ($90^\circ$)** | Left | **و** (Shift: `ھ`) | **ڪ** (Shift: `ط`) | **ٿ** (Shift: `ڍ`) | **ڙ** (Shift: `ث`) |
| **Left ($180^\circ$)** | Top | **ر** (Shift: `ٻ`) | **د** (Shift: `ڄ`) | **ش** (Shift: `ۂ`) | **گ** (Shift: `ٰ`) |
| **Left ($180^\circ$)** | Bottom | **ه** (Shift: `ڀ`) | **ب** (Shift: `ذ`) | **ڏ** (Shift: `ۃ`) | **ڊ** (Shift: `ـ`) |
| **Bottom ($270^\circ$)** | Left | **۾** (Shift: `ڌ`) | **پ** (Shift: `ڦ`) | **ق** (Shift: `ء`) | **ز** (Shift: `؛`) |
| **Bottom ($270^\circ$)** | Right | **ج** (Shift: `ٺ`) | **ئ** (Shift: `ڇ`) | **آ** (Shift: `ژ`) | **ح** (Shift: `!`) |

---

## 3. How to Rebuild and Install

### Rebuild:
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
