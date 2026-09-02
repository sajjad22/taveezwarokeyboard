# تعويذ وارو ڪيبورڊ (Taveez Waro Keyboard)

<p align="center">
  <img src="metadata/en-US/images/icon.png" alt="تعويذ وارو ڪيبورڊ" width="128" height="128" />
</p>

<p align="center">
  <b>سنڌي ٻوليءَ جو پهريون اشارن (Gestures) ۽ تعويذي ڦيٿي ذريعي تيز رفتار لکڻ وارو ڪيبورڊ</b><br>
  <sub>Public Beta: <code>v0.1.0</code> | Package: <code>com.sajjad.taveezwarokeyboard</code></sub>
</p>

<p align="center">
  <a href="https://github.com/sajjad22/taveezwarokeyboard/releases"><img src="https://img.shields.io/badge/Release-v0.1.0--beta-blue.svg" alt="Release v0.1.0-beta" /></a>
  <a href="https://www.apache.org/licenses/LICENSE-2.0"><img src="https://img.shields.io/badge/License-Apache%202.0-green.svg" alt="License" /></a>
  <a href="https://android.com"><img src="https://img.shields.io/badge/Android-7.0%2B%20(API%2024%2B)-orange.svg" alt="Android API" /></a>
</p>

---

## 🌟 پس منظر ۽ ڪريڊٽ (Credits & Attribution)

- **اصل تصور (Original Concept)**: هي ڪيبورڊ اصل ۾ انقلابي **8Pen** تصور مان متاثر ٿيل آهي، جيڪو اشارن ذريعي تيز ٽائپنگ لاءِ ٺاهيو ويو هو.
- **اوپن سورس بنياد (Upstream Fork)**: هي پروجيڪٽ اوپن سورس [**8Vim**](https://github.com/8VIM/8VIM) تان فورڪ ڪيو ويو آهي. اصل ڊولپرن جا بيحد ٿورائتا آهيون جن هي بنياد فراهم ڪيو.
- **سنڌي ورزن (Sindhi Redesign & Development)**: سنڌي ٻوليءَ جي 52 اکرن، اعرابن، مخصوص مرڪب اکرن ۽ لفظن جي استعمال جي شرح (Letter Frequencies) مطابق مڪمل نئين نموني ترتيب ڏنو ويو آهي.

---

## ✨ مکيه خاصيتون (Key Features)

### 1. سنڌي اکرن جي فريڪوئنسي مطابق تعويذي ڦيٿو (Frequency Layout)
- **پهرين ليول (دائري جي ويجهو سڀ کان اهم 8 اکر)**: `ي`, `ا`, `ن`, `و`, `ر`, `ه`, `م`, `ج`.
- **ٻي ليول (ٻيو گول چڪر)**: `س`, `ل`, `ت`, `ڪ`, `د`, `ب`, `پ`, `ئ`.
- **ٽئين ليول (ٽيون چڪر)**: `ک`, `ڻ`, `ع`, `ٿ`, `ش`, `ڏ`, `ق`, `ح`.
- **چوٿين ليول (ٻاهريون چڪر)**: `ٽ`, `ف`, `چ`, `ڙ`, `گ`, `خ`, `ز`, `آ`.

### 2. تڪڙا اشارا (Quick Gestures)
- `مرڪز ← هيٺ ← مرڪز`: **تڪڙو شفٽ آن / آف (Quick Shift Toggle)**.
- `مرڪز ← مٿي ← مرڪز`: وچ وارو تجويز ڪيل لفظ چونڊيو (Best Suggestion).
- `مرڪز ← کاٻي ← مرڪز`: کاٻي پاسي واري چِپ جو لفظ چونڊيو (Left Suggestion).
- `مرڪز ← ساڄي ← مرڪز`: ساڄي پاسي واري چِپ جو لفظ چونڊيو (Right Suggestion).
- **مرڪزي دائري تي ڊبل ٽيپ**: فل اسٽاپ (`.`) لکڻ لاءِ.

### 3. شفٽ ليئر ۽ اعرابون (Shift & Airabs)
- سڀ بنيادي اعرابون (`َ`, `ِ`, `ُ`, `ّ`) ساڄي مٿئين محور تي ترتيب ڏنل آهن.
- سڀ ثانوي سنڌي اکر (`ٻ`, `ڀ`, `ڌ`, `ٺ`, `ھ`, `ڳ`, `ڊ`, `ڇ`, `ء` وغيره) ۽ نشانيون (`،`, `ٰ`, `؟`) شفٽ ليئر تي هلڪي رنگ ۾ نمايان آهن.
- لکڻ دوران مٿين پٽي تي اکر سان گڏ ان جو شفٽ اکر هلڪي سائيز ۾ هِنٽ طور پيش ٿيندو آهي.

### 4. لفظن جون تجويزون (NLP Suggestions)
- **17,863 کان وڌيڪ سنڌي لفظن جو ڊيٽابيس**: لکڻ دوران خودڪار تجويزون.
- تجويزون رڳو تڏهن ظاهر ٿينديون جڏهن اکر ٽائپ ڪيا ويندا.

### 5. مڪمل سنڌي انٽرفيس ۽ خوبصورت ٿيمز
- سنڌي اجرڪ، زمرد سائو، شاهي نيرو، نيم رات ڪارو، ٿر جو وارياسو ۽ نيون ٿيمز.

---

## 📐 4-محوري سنڌي لئـائوٽ نقشو (Layout Map)

| حصو (Quadrant) | طرف (Spoke) | ليول 1 (مرڪز وٽ) | ليول 2 (ٻيو گول) | ليول 3 (ٽيون گول) | ليول 4 (ٻاهريون) |
|---|---|---|---|---|---|
| **ساڄو ($0^\circ$)** | مٿي | **ي** (Shift: `َ`) | **س** (Shift: `ِ`) | **ک** (Shift: `ُ`) | **ٽ** (Shift: `ّ`) |
| **ساڄو ($0^\circ$)** | هيٺ | **ا** (Shift: `ٻ`) | **ل** (Shift: `ڳ`) | **ڻ** (Shift: `ض`) | **ف** (Shift: `ث`) |
| **مٿيون ($90^\circ$)** | ساڄي | **ن** (Shift: `ڌ`) | **ت** (Shift: `،`) | **ع** (Shift: `ظ`) | **چ** (Shift: `ڍ`) |
| **مٿيون ($90^\circ$)** | کاٻي | **و** (Shift: `ھ`) | **ڪ** (Shift: `ط`) | **ٿ** (Shift: `ڃ`) | **ڙ** (Shift: `ڦ`) |
| **کاٻو ($180^\circ$)** | مٿي | **ر** (Shift: `ص`) | **د** (Shift: `ڊ`) | **ش** (Shift: `غ`) | **گ** (Shift: `ڱ`) |
| **کاٻو ($180^\circ$)** | هيٺ | **ه** (Shift: `ڀ`) | **ب** (Shift: `ڇ`) | **ڏ** (Shift: `ڄ`) | **خ** (Shift: `ء`) |
| **هيٺيون ($270^\circ$)** | کاٻي | **م** (Shift: `۾`) | **پ** (Shift: `ٰ`) | **ق** (Shift: `ذ`) | **ز** (Shift: `ـ`) |
| **هيٺيون ($270^\circ$)** | ساڄي | **ج** (Shift: `ٺ`) | **ئ** (Shift: `۽`) | **ح** (Shift: `؟`) | **آ** (Shift: `!`) |

---

## 📥 ڊائون لوڊ ۽ انسٽاليشن (Download & Testing)

1. گٽ هب ريليز مان سڌو APK حاصل ڪريو:
   👉 **[GitHub Releases / TaveezWaroKeyboard-debug.apk](https://github.com/sajjad22/taveezwarokeyboard/releases)**
2. فون ۾ انسٽال ڪري اينڊرائيڊ سيٽنگس مان فعال ڪريو.

---

## 🐛 مسئلا ۽ راءِ (Issues & Feedback)

جيڪڏهن اوهان کي ڪو نقص (Bug) نظر اچي يا ڪا نئين تجويز هجي، ته گٽ هب جي **[Issues](https://github.com/sajjad22/taveezwarokeyboard/issues)** صفحي تي نئون ايشو کولي اسان کي آگاهه ڪري سگهو ٿا.

---

## 🛠️ ڪيئن بلڊ ڪجي (How to Build)

```bash
git clone https://github.com/sajjad22/taveezwarokeyboard.git
cd taveezwarokeyboard
./build-apk.sh
```

---

## 📄 لائسنس (License)

This project is licensed under the Apache License 2.0 - see upstream [8Vim](https://github.com/8VIM/8VIM) repository for details.
