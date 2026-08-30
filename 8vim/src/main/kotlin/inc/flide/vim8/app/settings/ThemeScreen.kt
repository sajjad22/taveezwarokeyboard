package inc.flide.vim8.app.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import inc.flide.vim8.R
import inc.flide.vim8.datastore.model.observeAsState
import inc.flide.vim8.datastore.ui.ColorPreference
import inc.flide.vim8.datastore.ui.Preference
import inc.flide.vim8.datastore.ui.PreferenceGroup
import inc.flide.vim8.datastore.ui.SwitchPreference
import inc.flide.vim8.lib.compose.Dialog
import inc.flide.vim8.lib.compose.Screen
import inc.flide.vim8.lib.compose.stringRes
import inc.flide.vim8.theme.ThemeMode

private fun themeTitle(mode: ThemeMode): String = when (mode) {
    ThemeMode.SYSTEM -> "سسٽم ڊفالٽ"
    ThemeMode.LIGHT -> "روشن اڇو"
    ThemeMode.DARK -> "گهاٽو ڪارو"
    ThemeMode.SINDHI_AJRAK -> "سنڌي اجرڪ"
    ThemeMode.EMERALD_NIGHT -> "زمرد سائو"
    ThemeMode.ROYAL_INDIGO -> "شاهي نيرو"
    ThemeMode.MIDNIGHT_OLED -> "نيم رات ڪارو"
    ThemeMode.SUNSET_DESERT -> "ٿر جو وارياسو"
    ThemeMode.CYBERPUNK_NEON -> "نيون روشني"
    ThemeMode.CUSTOM -> "پنهنجي مرضي جا رنگ"
}

private val modes = ThemeMode.entries.map { themeTitle(it) }

@Composable
fun ThemeScreen() = Screen {
    title = stringRes(R.string.settings__theme__title)
    previewFieldVisible = true

    content {
        val colorMode by prefs.theme.mode.observeAsState()
        val trailIsVisible by prefs.keyboard.trail.isVisible.observeAsState()
        val trailUseRandomColor by prefs.keyboard.trail.useRandomColor.observeAsState()

        PreferenceGroup {
            Dialog {
                title = stringRes(R.string.settings__theme__color__mode__title)
                index = { colorMode.ordinal }
                items = { modes }
                onConfirm { prefs.theme.mode.set(ThemeMode.entries[it]) }
                Preference(
                    title = stringRes(R.string.settings__theme__color__mode__title),
                    summary = themeTitle(colorMode),
                    onClick = { show() }
                )
            }
            ColorPreference(
                prefs.keyboard.customColors.background,
                title = stringRes(R.string.settings__theme__custom__background__color__title),
                visibleIf = { colorMode == ThemeMode.CUSTOM }
            )
            ColorPreference(
                prefs.keyboard.customColors.foreground,
                title = stringRes(R.string.settings__theme__custom__foreground__color__title),
                visibleIf = { colorMode == ThemeMode.CUSTOM }
            )
        }

        PreferenceGroup {
            SwitchPreference(
                prefs.keyboard.trail.isVisible,
                title = stringRes(R.string.settings__theme__trail__is__visible__title),
                summaryOff = stringRes(
                    R.string.settings__theme__trail__is__visible__summary__off
                ),
                summaryOn = stringRes(
                    R.string.settings__theme__trail__is__visible__summary__on
                )
            )
            SwitchPreference(
                prefs.keyboard.trail.useRandomColor,
                title = stringRes(R.string.settings__theme__trail__use__random__color__title),
                summaryOff = stringRes(
                    R.string.settings__theme__trail__use__random__color__summary__off
                ),
                summaryOn = stringRes(
                    R.string.settings__theme__trail__use__random__color__summary__on
                ),
                visibleIf = { trailIsVisible }
            )
            ColorPreference(
                prefs.keyboard.trail.color,
                title = stringRes(R.string.settings__theme__trail__color__title),
                visibleIf = { trailIsVisible && !trailUseRandomColor }
            )
        }
    }
}
