# تعويذ وارو ڪيبورڊ — Library Analysis & Improvement Suggestions

## 📦 Libraries in Use (27 production + testing)

---

### 🟢 Core Android / UI

| Library | Version | Purpose | Verdict |
|---|---|---|---|
| `androidx.core:core-ktx` | 1.15.0 | Kotlin extensions for Android APIs (context helpers, etc.) | ✅ Keep |
| `androidx.activity:activity-compose` | 1.12.4 | Bridges `Activity` lifecycle to Compose | ✅ Keep |
| `androidx.activity:activity-ktx` | 1.12.4 | Kotlin helpers for `Activity` (e.g., `registerForActivityResult`) | ✅ Keep |
| `androidx.appcompat:appcompat` | 1.7.1 | Legacy view theme & `AppCompatDelegate` (night mode) | ⚠️ Partially used |
| `com.google.android.material:material` | 1.13.0 | Material Design Views (XML-era) | ⚠️ Likely unused |
| `androidx.preference:preference` | 1.2.1 | Old XML-based `SharedPreferences` UI | ⚠️ Minimal usage |

> **Note on `appcompat` + `material`:** The app uses Jetpack Compose almost exclusively. `appcompat` is still needed for `AppCompatDelegate.setDefaultNightMode()` (night mode) in [`VIM8Application.kt`](file:///home/sajjad/sindhivim/8vim/src/main/kotlin/inc/flide/vim8/VIM8Application.kt). But `com.google.android.material` is an XML-era Views library — if no `.xml` layouts reference Material components directly, it can be removed.

---

### 🟢 Jetpack Compose

| Library | Version | Purpose | Verdict |
|---|---|---|---|
| `androidx.compose.ui:ui` | 1.10.4 | Core Compose rendering engine | ✅ Keep |
| `androidx.compose.material3:material3` | 1.4.0 | Material 3 Compose components (buttons, cards, etc.) | ✅ Keep |
| `androidx.compose.material:material-icons-core` | 1.7.8 | Built-in Compose icons | ✅ Keep |
| `androidx.navigation:navigation-compose` | 2.9.7 | Screen-to-screen navigation in Compose | ✅ Keep |
| `androidx.lifecycle:lifecycle-runtime-ktx` | 2.10.0 | `collectAsStateWithLifecycle`, coroutine-aware lifecycle | ✅ Keep |
| `androidx.core:core-splashscreen` | 1.2.0 | Animated splash screen on app open | ✅ Keep |

---

### 🟢 Functional Programming — Arrow-kt

| Library | Version | Purpose | Verdict |
|---|---|---|---|
| `io.arrow-kt:arrow-core` | 1.2.4 | Functional types: `Either`, `Option`, `None`, `Some` | ✅ Keep (deeply used) |
| `io.arrow-kt:arrow-optics` | 1.2.4 | Immutable data class lenses (auto-generated copy helpers) | ✅ Keep |
| `io.arrow-kt:arrow-optics-ksp-plugin` | 1.2.4 | KSP code generator for optics | ✅ Keep |
| `io.arrow-kt:arrow-integrations-jackson-module` | 0.14.0 | Arrow `Either` / `Option` serialization via Jackson | ⚠️ Check if needed |

> `arrow-integrations-jackson-module` is older (0.14.0) and exists to serialize Arrow types with Jackson. It's used when backing up/restoring settings. If backup files don't contain `Either`/`Option` fields directly, this module may not be needed.

---

### 🟢 Serialization — Jackson

| Library | Version | Purpose | Verdict |
|---|---|---|---|
| `jackson-dataformat-yaml` | 2.18.6 | Parses `sd.yaml`, `en.yaml`, gesture files | ✅ Essential |
| `jackson-dataformat-cbor` | 2.18.6 | Binary format for cached layout (faster startup) | ✅ Keep |
| `jackson-module-kotlin` | 2.18.6 | Allows Jackson to serialize Kotlin data classes | ✅ Keep |

---

### 🟢 Validation

| Library | Version | Purpose | Verdict |
|---|---|---|---|
| `com.networknt:json-schema-validator` | 1.0.73 | Validates YAML keyboard layout files against a JSON schema | ✅ Keep |

---

### 🟡 Utilities

| Library | Version | Purpose | Verdict |
|---|---|---|---|
| `org.apache.commons:commons-text` | 1.15.0 | Used for `DigestUtils`-like hashing in [`Layout.kt`](file:///home/sajjad/sindhivim/8vim/src/main/kotlin/inc/flide/vim8/ime/layout/Layout.kt) | ⚠️ Could replace with `java.security.MessageDigest` |
| `commons-codec:commons-codec` | 1.17.1 | Provides hex/base64 encoding helpers | ⚠️ May overlap with Apache Commons |
| `org.slf4j:slf4j-api` | 2.0.16 | Logging facade API | 🔴 **Unused in main code** |
| `com.github.tony19:logback-android` | 3.0.0 | SLF4J backend for Android (writes to file/logcat) | 🔴 **Unused in main code** |

---

### 🟢 UI Extras

| Library | Version | Purpose | Verdict |
|---|---|---|---|
| `com.github.skydoves:colorpicker-compose` | 1.2.0 | Compose color wheel/picker for custom keyboard themes | ✅ Keep |
| `com.mikepenz:aboutlibraries-core` | 11.2.3 | Auto-generates list of open-source library licenses | ✅ Keep |
| `com.mikepenz:aboutlibraries-compose-m3` | 11.2.3 | Compose UI for showing those licenses | ✅ Keep |

---

### 🧪 Testing Libraries

| Library | Purpose |
|---|---|
| `io.kotest:kotest-runner-junit5` | Test framework (alternative to JUnit 5) |
| `io.kotest:kotest-assertions-core` | Fluent `shouldBe` assertions |
| `io.kotest:kotest-framework-datatest` | Data-driven / parameterized tests |
| `io.kotest:kotest-property` | Property-based testing |
| `io.kotest.extensions:kotest-assertions-arrow` | Arrow-specific matchers for tests |
| `io.kotest.extensions:kotest-property-arrow` | Property testing with Arrow types |
| `io.mockk:mockk` | Kotlin-idiomatic mocking framework |
| `ch.qos.logback:logback-classic` | Logging backend for tests only |
| `androidx.test:core`, `runner`, `rules` | Android instrumentation test infrastructure |
| `br.com.colman:kotest-runner-android` | Run Kotest tests on Android device/emulator |

---

## 🛠️ Suggestions — What to Change/Improve

### 1. 🔴 Remove Unused Logging Libraries (`slf4j` + `logback-android`)

`slf4j-api` and `logback-android` are declared as `implementation` dependencies — meaning they are **shipped inside the APK** (adding ~500KB) — but **no Kotlin file in `src/main` imports or uses them**. All logging currently happens via Android's built-in `android.util.Log`.

**Action:** Remove both from `build.gradle.kts`:
```kotlin
// Remove these two lines:
implementation(libs.slf4j.api)
implementation(libs.logback.android)
```
And remove from `libs.versions.toml`:
```toml
# Remove:
slf4j = "2.0.16"
logback-android = "3.0.0"
```
Keep `logback-classic` only in `testImplementation` (it's already there and correct).

---

### 2. 🟡 Consider Removing `com.google.android.material`

The project is **100% Jetpack Compose** for its UI. The XML-era `material` library is included but likely not directly referenced. Check with:
```bash
grep -r "com.google.android.material" 8vim/src/main/res/
```
If nothing in XML layouts uses it, it can be safely removed to reduce APK size.

---

### 3. 🟡 Replace `apache-commons-text` + `commons-codec` with stdlib

Both libraries are used for hashing layout files to detect changes ([`Layout.kt`](file:///home/sajjad/sindhivim/8vim/src/main/kotlin/inc/flide/vim8/ime/layout/Layout.kt)). The same can be done with Java's built-in `java.security.MessageDigest`:
```kotlin
// Replace Apache DigestUtils.md5Hex(str) with:
import java.security.MessageDigest
fun String.md5(): String {
    val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}
```
This removes **two** library dependencies and reduces APK size by ~400KB.

---

### 4. 🟡 Update `androidx.preference` to DataStore

[`PreferenceModel.kt`](file:///home/sajjad/sindhivim/8vim/src/main/kotlin/inc/flide/vim8/datastore/model/PreferenceModel.kt) imports `androidx.preference` — an old `SharedPreferences`-based library. The app already has a custom `datastore/` folder for preferences. Migrating fully to **Jetpack DataStore (Preferences)** would bring:
- Type-safety
- Coroutine/Flow-based reactive updates
- No more XML `<PreferenceScreen>` legacy

---

### 5. 🟢 Good Architecture Choices (Keep These)

- **Arrow-kt `Either`/`Option`**: Excellent for error handling in the layout parser (no null crashes, explicit error types).
- **Kotest + Property Testing**: Great for testing gesture/layout logic exhaustively.
- **Jackson YAML + CBOR**: Smart two-step approach — parse YAML once, cache as fast binary CBOR for startup performance.
- **Kotlin Coroutines** (`appScope`, `Dispatchers.IO`): Dictionary seeding off the main thread correctly.

---

## 📉 Estimated APK Size Savings

| Change | Estimated Saving |
|---|---|
| Remove `slf4j-api` + `logback-android` | ~500 KB |
| Remove `commons-codec` + `commons-text` | ~400 KB |
| Remove `com.google.android.material` (if unused) | ~300 KB |
| **Total** | **~1.2 MB reduction** |

> Current debug APK: **22 MB**. Release (minified + ProGuard) would be significantly smaller already (~8–12 MB), but removing unused libraries still helps.

---

## 🔢 Summary Table

| # | Action | Effort | Impact |
|---|---|---|---|
| 1 | Remove `slf4j` + `logback-android` from main deps | Low ⭐ | APK size ↓, cleaner build |
| 2 | Remove `com.google.android.material` if unused in XML | Low ⭐ | APK size ↓ |
| 3 | Replace `apache-commons` + `commons-codec` with stdlib | Medium ⭐⭐ | APK size ↓, fewer deps |
| 4 | Migrate `androidx.preference` → Jetpack DataStore | High ⭐⭐⭐ | Type-safe, reactive prefs |
| 5 | Add ProGuard/R8 release rules (signing) | Medium ⭐⭐ | 60–70% APK size reduction |

