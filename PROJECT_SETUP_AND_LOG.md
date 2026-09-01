# تعويذ وارو ڪيبورڊ (Taveez Waro Keyboard) - Development & Setup Log

This document records the full history of the project, user requests, agent recommendations, environment setup details (what was installed on your PC, where, and why), design decisions for the 4-axis Sindhi Taveez keyboard layout, letter frequency ranking, faded Shift keys, colored axis, custom app icon, UI translations, and build instructions.

---

## 1. Project Overview & Request History

### User Requests & Summary of Features:

1. **New Name & Identity: تعويذ وارو ڪيبورڊ (Taveez Waro Keyboard)**:
   - App label and launcher name set to **تعويذ وارو ڪيبورڊ**.
   - Custom app vector and high-res icon matching the Taveez / 4-axis geometric layout.
2. **Fixed `م` and `۾` Placement**:
   - Standard Meem (`م`) restored to Level 1 (most frequent) on the main layer.
   - Preposition (`۾`) placed at Level 1 on the Shift layer.
3. **Punctuation on Main Layer**:
   - Sindhi question mark (`؟`) placed on the main layer.
   - Sindhi comma (`،`) and colon (`:`) placed on the Shift layer.
4. **Airabs Spoke on Shift Layer**:
   - Dedicated Right-Top spoke on Shift layer for basic airabs: `َ` (Zabar), `ِ` (Zer), `ُ` (Pesh), `ّ` (Tashdeed).
5. **Single-Layer Mode**:
   - Removed `extra_layers` multi-layer rotation wheel.
   - Clean, standard English digits & symbols numpad (`NumberLayout.kt`).
6. **Clipboard Integration**:
   - Direct clipboard history toggle added to the keyboard sidebar.
7. **Dual Language Prediction Engine (English & Sindhi)**:
   - English word predictions on English layout (`assets/word_seed_en.csv`).
   - Sindhi word predictions on Sindhi layout (`assets/word_seed_sd.csv` with 17,863 high-frequency words).
8. **Double-Tap Circle for Full Stop**:
   - Double-tapping inside the center circle inserts a full stop (`.`).
   - Configurable from **اشارا (Gestures)** settings: "مرڪز تي ڊبل ٽيپ سان فل اسٽاپ".
9. **About Page (ڪيبورڊ بابت)**:
   - Clear introduction in Sindhi and English explaining the Taveez Waro Keyboard concept and 8Pen inspiration.

---

## 2. Updated 4-Axis Frequency Layout (`sd.yaml`)

| Quadrant | Spoke | Level 1 (Inner) | Level 2 | Level 3 | Level 4 (Outer) |
|---|---|---|---|---|---|
| **Right ($0^\circ$)** | Top | **ي** (Shift: `َ`) | **س** (Shift: `ِ`) | **ک** (Shift: `ُ`) | **ٽ** (Shift: `ّ`) |
| **Right ($0^\circ$)** | Bottom | **ا** (Shift: `ٻ`) | **ل** (Shift: `ڳ`) | **ڻ** (Shift: `ض`) | **ف** (Shift: `ث`) |
| **Top ($90^\circ$)** | Right | **ن** (Shift: `ڌ`) | **ت** (Shift: `،`) | **ع** (Shift: `ظ`) | **چ** (Shift: `ڍ`) |
| **Top ($90^\circ$)** | Left | **و** (Shift: `ھ`) | **ڪ** (Shift: `ط`) | **ٿ** (Shift: `ڃ`) | **ڙ** (Shift: `ڦ`) |
| **Left ($180^\circ$)** | Top | **ر** (Shift: `ص`) | **د** (Shift: `ڊ`) | **ش** (Shift: `غ`) | **گ** (Shift: `ڱ`) |
| **Left ($180^\circ$)** | Bottom | **ه** (Shift: `ڀ`) | **ب** (Shift: `ڇ`) | **ڏ** (Shift: `ڄ`) | **خ** (Shift: `ء`) |
| **Bottom ($270^\circ$)** | Left | **م** (Shift: `۾`) | **پ** (Shift: `ٰ`) | **ق** (Shift: `ذ`) | **ز** (Shift: `ـ`) |
| **Bottom ($270^\circ$)** | Right | **ج** (Shift: `ٺ`) | **ئ** (Shift: `۽`) | **ح** (Shift: `؟`) | **آ** (Shift: `!`) |

---

## 3. How to Rebuild and Install

### Rebuild:
```bash
./build-apk.sh
```

### Ready APK:
👉 **[`/home/sajjad/sindhivim/TaveezWaroKeyboard-debug.apk`](file:///home/sajjad/sindhivim/TaveezWaroKeyboard-debug.apk)** *(Size: 24 MB)*

### Transfer to Phone:
```bash
python3 -m http.server 8080
```
Open `http://<laptop-ip>:8080/TaveezWaroKeyboard-debug.apk` on your phone browser and tap install.
