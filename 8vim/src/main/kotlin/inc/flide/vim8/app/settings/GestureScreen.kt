package inc.flide.vim8.app.settings

import androidx.compose.runtime.Composable
import inc.flide.vim8.R
import inc.flide.vim8.datastore.ui.PreferenceGroup
import inc.flide.vim8.datastore.ui.SwitchPreference
import inc.flide.vim8.lib.compose.Screen
import inc.flide.vim8.lib.compose.stringRes

@Composable
fun GestureScreen() = Screen {
    title = stringRes(R.string.settings__gesture__title)
    previewFieldVisible = true
    content {
        PreferenceGroup {
            SwitchPreference(
                prefs.keyboard.behavior.cursor.moveByWord,
                title = stringRes(R.string.settings__gesture__cursor__move__by__word__title),
                summaryOff = stringRes(
                    R.string.settings__gesture__cursor__move__by__word__summary__off
                ),
                summaryOn = stringRes(
                    R.string.settings__gesture__cursor__move__by__word__summary__on
                )
            )
            SwitchPreference(
                prefs.keyboard.behavior.fnEnabled,
                title = stringRes(R.string.settings__gesture__fn_enabled__title),
                summary = stringRes(R.string.settings__gesture__fn_enabled__summary)
            )
            SwitchPreference(
                prefs.keyboard.behavior.doubleTapCirclePeriod,
                title = stringRes(R.string.settings__gesture__double_tap_circle_period__title),
                summary = stringRes(R.string.settings__gesture__double_tap_circle_period__summary)
            )
        }
    }
}
