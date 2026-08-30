package inc.flide.vim8.ime.theme

import android.content.Context
import android.content.res.Configuration
import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import inc.flide.vim8.AppPrefs
import inc.flide.vim8.appPreferenceModel
import inc.flide.vim8.lib.android.isDarkTheme
import inc.flide.vim8.theme.ThemeMode
import inc.flide.vim8.theme.darkColorPalette
import inc.flide.vim8.theme.lightColorPalette
import kotlin.random.Random

class ThemeManager(context: Context) {
    private val prefs: AppPrefs by appPreferenceModel()
    private val randomTrailColor = RandomTrailColor()
    private val darkColorScheme: ColorScheme = darkColorPalette(context)
    private val lightColorScheme: ColorScheme = lightColorPalette(context)
    private val systemColorScheme: ColorScheme
        get() = if (configuration.isDarkTheme()) darkColorScheme else lightColorScheme

    var configuration: Configuration = context.resources.configuration
        set(value) {
            field = value
            updateCurrentTheme()
        }
    private val _currentTheme = MutableLiveData<ThemeInfo>()
    val currentTheme: LiveData<ThemeInfo> get() = _currentTheme

    init {
        _currentTheme.value = themeInfo()
        val customColors = prefs.keyboard.customColors
        val trailColor = prefs.keyboard.trail
        prefs.theme.mode.observe {
            updateCurrentTheme()
        }
        customColors.background.observe { updateCurrentTheme() }
        customColors.foreground.observe { updateCurrentTheme() }
        trailColor.useRandomColor.observe { updateCurrentTheme() }
        trailColor.color.observe { updateCurrentTheme() }
    }

    fun updateCurrentTheme() {
        _currentTheme.postValue(themeInfo())
    }

    private fun themeInfo(): ThemeInfo = ThemeInfo(colorScheme(), trailColor())

    private fun colorScheme(): ColorScheme {
        val mode = prefs.theme.mode.get()
        return when (mode) {
            ThemeMode.CUSTOM -> {
                val customColorsPrefs = prefs.keyboard.customColors
                val backgroundColor = Color(customColorsPrefs.background.get())
                val foregroundColor = Color(customColorsPrefs.foreground.get())
                systemColorScheme.copy(
                    surface = backgroundColor,
                    onSurface = foregroundColor,
                    primary = foregroundColor
                )
            }
            ThemeMode.DARK -> darkColorScheme
            ThemeMode.LIGHT -> lightColorScheme
            ThemeMode.SINDHI_AJRAK -> systemColorScheme.copy(
                surface = Color(0xFF1E0E14),
                onSurface = Color(0xFFFFD1D9),
                primary = Color(0xFFE11D48),
                onPrimary = Color(0xFFFFFFFF),
                background = Color(0xFF14080B),
                onBackground = Color(0xFFFFE4E6),
                surfaceVariant = Color(0xFF2E131E),
                onSurfaceVariant = Color(0xFFFDA4AF)
            )
            ThemeMode.EMERALD_NIGHT -> systemColorScheme.copy(
                surface = Color(0xFF06281E),
                onSurface = Color(0xFFA7F3D0),
                primary = Color(0xFF10B981),
                onPrimary = Color(0xFFFFFFFF),
                background = Color(0xFF021711),
                onBackground = Color(0xFFD1FAE5),
                surfaceVariant = Color(0xFF0D3D2F),
                onSurfaceVariant = Color(0xFF6EE7B7)
            )
            ThemeMode.ROYAL_INDIGO -> systemColorScheme.copy(
                surface = Color(0xFF0F172A),
                onSurface = Color(0xFFFDE047),
                primary = Color(0xFF38BDF8),
                onPrimary = Color(0xFF0F172A),
                background = Color(0xFF020617),
                onBackground = Color(0xFFE2E8F0),
                surfaceVariant = Color(0xFF1E293B),
                onSurfaceVariant = Color(0xFF94A3B8)
            )
            ThemeMode.MIDNIGHT_OLED -> systemColorScheme.copy(
                surface = Color(0xFF000000),
                onSurface = Color(0xFF00E5FF),
                primary = Color(0xFF00E5FF),
                onPrimary = Color(0xFF000000),
                background = Color(0xFF000000),
                onBackground = Color(0xFFFFFFFF),
                surfaceVariant = Color(0xFF121212),
                onSurfaceVariant = Color(0xFF80D8FF)
            )
            ThemeMode.SUNSET_DESERT -> systemColorScheme.copy(
                surface = Color(0xFF2C1810),
                onSurface = Color(0xFFFED7AA),
                primary = Color(0xFFF97316),
                onPrimary = Color(0xFFFFFFFF),
                background = Color(0xFF1C0E07),
                onBackground = Color(0xFFFFEDD5),
                surfaceVariant = Color(0xFF3D2317),
                onSurfaceVariant = Color(0xFFFDBA74)
            )
            ThemeMode.CYBERPUNK_NEON -> systemColorScheme.copy(
                surface = Color(0xFF180A2A),
                onSurface = Color(0xFFF43F5E),
                primary = Color(0xFF06B6D4),
                onPrimary = Color(0xFFFFFFFF),
                background = Color(0xFF0D021A),
                onBackground = Color(0xFFE879F9),
                surfaceVariant = Color(0xFF261042),
                onSurfaceVariant = Color(0xFFC084FC)
            )
            else -> systemColorScheme
        }
    }

    private fun trailColor(): TrailColor = if (prefs.keyboard.trail.useRandomColor.get()) {
        randomTrailColor
    } else {
        FixedTrailColor(Color(prefs.keyboard.trail.color.get()))
    }

    data class ThemeInfo(val scheme: ColorScheme, val trailColor: TrailColor)

    interface TrailColor {
        fun color(): Color
    }

    class FixedTrailColor(private val color: Color) : TrailColor {
        override fun color(): Color = color
    }

    class RandomTrailColor(private val random: Random = Random.Default) : TrailColor {
        override fun color(): Color = Color(
            random.nextInt(256),
            random.nextInt(256),
            random.nextInt(256),
            255
        )
    }
}

fun Color.blendARGB(other: Color, ratio: Float): Color =
    Color(ColorUtils.blendARGB(this.toArgb(), other.toArgb(), ratio))
