package inc.flide.vim8.ime.keyboard.view

import android.content.Intent
import android.view.KeyEvent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import inc.flide.vim8.R
import inc.flide.vim8.app.MainActivity
import inc.flide.vim8.appPreferenceModel
import inc.flide.vim8.datastore.model.observeAsState
import inc.flide.vim8.ime.clipboard.ClipboardLayout
import inc.flide.vim8.ime.input.ImeUiMode
import inc.flide.vim8.ime.keyboard.text.toKeyboardAction
import inc.flide.vim8.ime.keyboard.xpad.XpadLayout
import inc.flide.vim8.ime.layout.models.CustomKeycode
import inc.flide.vim8.ime.ui.KeyboardLayoutMode
import inc.flide.vim8.keyboardManager
import inc.flide.vim8.lib.android.launchActivity
import inc.flide.vim8.lib.compose.ImageButton
import inc.flide.vim8.lib.compose.stringRes

@Composable
fun KeyboardLayout() {
    val prefs by appPreferenceModel()
    val context = LocalContext.current
    val keyboardManager by context.keyboardManager()

    val isOnLeft by prefs.keyboard.sidebar.isOnLeft.observeAsState()
    val state by keyboardManager.activeState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Row {
                if (isOnLeft) Sidebar()
                Column(
                    modifier = Modifier
                        .weight(5f)
                ) {
                    if (state.imeUiMode == ImeUiMode.TEXT) {
                        XpadLayout()
                    } else {
                        ClipboardLayout()
                    }
                }
                if (!isOnLeft) Sidebar()
            }
        }
    }
}

@Composable
fun RowScope.Sidebar() {
    val prefs by appPreferenceModel()

    val isVisible by prefs.keyboard.sidebar.isVisible.observeAsState()
    val keyboardLayoutMode by prefs.keyboard.layoutMode.mode.observeAsState()

    if (!isVisible) return

    val context = LocalContext.current
    val keyboardManager by context.keyboardManager()
    val inputEventDispatcher = keyboardManager.inputEventDispatcher
    val state by keyboardManager.activeState.collectAsState()

    val imm = context.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as? android.view.inputmethod.InputMethodManager

    Column(modifier = Modifier.weight(1f)) {
        ImageButton(
            resourceId = R.drawable.ic_language,
            description = stringRes(R.string.settings__layouts__title),
            onClick = {
                val current = prefs.layout.current.get()
                val nextLayout = if (current.path.toString().contains("sd")) {
                    inc.flide.vim8.ime.layout.EmbeddedLayout("en")
                } else {
                    inc.flide.vim8.ime.layout.EmbeddedLayout("sd")
                }
                prefs.layout.current.set(nextLayout)
            }
        )

        ImageButton(
            resourceId = R.drawable.ic_keyboard,
            description = stringRes(R.string.select_preferred_emoticon_keyboard_dialog_title),
            onClick = { imm?.showInputMethodPicker() }
        )

        ImageButton(
            resourceId = R.drawable.ic_open_with_black,
            description = stringRes(R.string.open_selection_keypad_button_content_description),
            onClick = {
                inputEventDispatcher.sendDownUp(
                    CustomKeycode.SWITCH_TO_SELECTION_KEYPAD.toKeyboardAction()
                )
            }
        )

        ImageButton(
            resourceId = if (state.isCtrlOn) R.drawable.ic_ctrl_engaged else R.drawable.ic_ctrl,
            description = stringRes(R.string.ctrl_button_content_description),
            onClick = {
                inputEventDispatcher.sendDownUp(
                    CustomKeycode.CTRL_TOGGLE.toKeyboardAction()
                )
            }
        )

        ImageButton(
            resourceId = if (state.isFnOn) R.drawable.ic_fn_engaged else R.drawable.ic_fn,
            description = stringRes(R.string.settings__gesture__fn_enabled__title),
            onClick = {
                inputEventDispatcher.sendDownUp(
                    CustomKeycode.FN_TOGGLE.toKeyboardAction()
                )
            }
        )

        ImageButton(
            resourceId = R.drawable.clipboard,
            description = stringRes(R.string.clipboard_button_content_description),
            onClick = {
                val nextMode = if (state.imeUiMode == ImeUiMode.CLIPBOARD) {
                    ImeUiMode.TEXT
                } else {
                    ImeUiMode.CLIPBOARD
                }
                state.imeUiMode = nextMode
            }
        )

        ImageButton(
            resourceId = R.drawable.ic_keyboard_onscreen,
            description = stringRes(R.string.floating_keyboard_button_content_description),
            onClick = {
                val layoutMode = if (keyboardLayoutMode == KeyboardLayoutMode.EMBEDDED) {
                    KeyboardLayoutMode.FLOATING
                } else {
                    KeyboardLayoutMode.EMBEDDED
                }
                prefs.keyboard.layoutMode.mode.set(layoutMode)
            }
        )

        ImageButton(
            resourceId = R.drawable.key_icon_settings,
            description = stringRes(R.string.open_keyboard_settings_button_content_description),
            onClick = {
                context.launchActivity(MainActivity::class) {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
            }
        )
    }
}
