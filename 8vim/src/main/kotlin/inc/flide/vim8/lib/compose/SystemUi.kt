package inc.flide.vim8.lib.compose

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.inputmethodservice.InputMethodService
import android.view.View
import android.view.Window
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat
import inc.flide.vim8.ime.theme.ImeTheme
import inc.flide.vim8.lib.android.AndroidVersion
import inc.flide.vim8.theme.isLightTheme

@Composable
fun SystemUiApp() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colorScheme.isLightTheme()

    SideEffect {
        systemUiController
            .setStatusBarColor(
                color = Color.Transparent,
                darkIcons = useDarkIcons
            )
        systemUiController.setNavigationBarColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons,
            navigationBarContrastEnforced = false
        )
    }
}

@Composable
fun SystemUiIme() {
    val systemUiController = rememberSystemUiController()
    val currentTheme = ImeTheme.current
    val useDarkIcons = currentTheme.scheme.isLightTheme()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )
        systemUiController.setNavigationBarColor(
            color = currentTheme.scheme.background,
            darkIcons = useDarkIcons,
            navigationBarContrastEnforced = true
        )
    }
}

@Composable
private fun rememberSystemUiController(): AppSystemUiController {
    val view = LocalView.current
    return remember(view) { AppSystemUiController(view) }
}

private class AppSystemUiController(view: View) {
    private val window = view.context.findWindow()
    private val windowInsetsController = window?.let { WindowInsetsControllerCompat(it, view) }

    fun setStatusBarColor(color: Color, darkIcons: Boolean) {
        val window = window ?: return
        val windowInsetsController = windowInsetsController ?: return
        statusBarDarkContentEnabled = darkIcons

        window.statusBarColor = when {
            darkIcons && !windowInsetsController.isAppearanceLightStatusBars -> {
                Color(0f, 0f, 0f, 0.3f).compositeOver(color)
            }

            else -> color
        }.toArgb()
    }

    fun setNavigationBarColor(
        color: Color,
        darkIcons: Boolean,
        navigationBarContrastEnforced: Boolean
    ) {
        val window = window ?: return
        if (AndroidVersion.ATLEAST_API26_O) {
            navigationBarDarkContentEnabled = darkIcons
            isNavigationBarContrastEnforced = navigationBarContrastEnforced

            window.navigationBarColor = color.toArgb()
        }
    }

    var statusBarDarkContentEnabled: Boolean
        get() = windowInsetsController?.isAppearanceLightStatusBars ?: false
        set(value) {
            windowInsetsController?.isAppearanceLightStatusBars = value
        }

    var navigationBarDarkContentEnabled: Boolean
        get() = windowInsetsController?.isAppearanceLightNavigationBars ?: false
        set(value) {
            windowInsetsController?.isAppearanceLightNavigationBars = value
        }

    var isNavigationBarContrastEnforced: Boolean
        get() = AndroidVersion.ATLEAST_API29_Q && (window?.isNavigationBarContrastEnforced ?: false)
        set(value) {
            if (AndroidVersion.ATLEAST_API29_Q) {
                window?.isNavigationBarContrastEnforced = value
            }
        }
}

private tailrec fun Context.findWindow(): Window? {
    return when (this) {
        is Activity -> window
        is InputMethodService -> window?.window
        is ContextWrapper -> baseContext?.findWindow()
        else -> null
    }
}
