package inc.flide.vim8.ime.keyboard.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inc.flide.vim8.Vim8ImeService
import inc.flide.vim8.ime.nlp.SuggestionsManager
import inc.flide.vim8.keyboardManager
import inc.flide.vim8.suggestionsManager

/** Fixed height of the bar — keyboard layout size never changes. */
private val BAR_HEIGHT = 40.dp

/**
 * A horizontal bar that always occupies [BAR_HEIGHT] and shows either:
 * 1) A live letter preview pop badge while actively swiping/hovering a key, or
 * 2) The suggestion chips when not gliding.
 */
@Composable
fun SuggestionsBar() {
    val context = LocalContext.current
    val keyboardManager by context.keyboardManager()
    val suggestionsManager by context.suggestionsManager()
    val suggestions by suggestionsManager.suggestions.collectAsState()
    val previewChar = keyboardManager.previewChar.value

    if (!previewChar.isNullOrEmpty()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(BAR_HEIGHT)
                .padding(horizontal = 4.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                tonalElevation = 6.dp
            ) {
                Text(
                    text = previewChar,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 32.dp, vertical = 2.dp)
                )
            }
        }
    } else {
        val slots = listOf(
            // left — third most plausible
            suggestions.getOrNull(2),
            // centre — most plausible (best)
            suggestions.getOrNull(0),
            // right — second most plausible
            suggestions.getOrNull(1)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(BAR_HEIGHT)
                .padding(horizontal = 4.dp, vertical = 3.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            slots.forEach { word ->
                SuggestionSlot(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    word = word,
                    onSelect = { chosen -> commitSuggestion(suggestionsManager, chosen) }
                )
            }
        }
    }
}

@Composable
private fun SuggestionSlot(
    modifier: Modifier = Modifier,
    word: String?,
    onSelect: (String) -> Unit
) {
    SuggestionChip(
        modifier = modifier,
        enabled = word != null,
        onClick = { if (word != null) onSelect(word) },
        label = {
            if (word != null) {
                AutoSizeText(
                    text = word,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}

/**
 * Text that starts at [maxFontSize] and shrinks by 10 % each recomposition cycle
 * until the text fits without visual overflow, down to [minFontSize].
 * [remember(text)] resets the font size whenever the displayed word changes.
 */
@Composable
private fun AutoSizeText(
    text: String,
    modifier: Modifier = Modifier,
    maxFontSize: TextUnit = 14.sp,
    minFontSize: TextUnit = 8.sp
) {
    var fontSize by remember(text) { mutableStateOf(maxFontSize) }

    Text(
        text = text,
        modifier = modifier,
        fontSize = fontSize,
        maxLines = 1,
        softWrap = false,
        overflow = TextOverflow.Clip,
        textAlign = TextAlign.Center,
        onTextLayout = { result ->
            if (result.hasVisualOverflow && fontSize > minFontSize) {
                fontSize = (fontSize.value * 0.9f)
                    .coerceAtLeast(minFontSize.value).sp
            }
        }
    )
}

private fun commitSuggestion(suggestionsManager: SuggestionsManager, suggestion: String) {
    val ic = Vim8ImeService.currentInputConnection() ?: return
    val wordLength = suggestionsManager.currentWordLength
    if (wordLength > 0) {
        ic.deleteSurroundingText(wordLength, 0)
    }
    ic.commitText("$suggestion ", 1)
    // Record the tapped word so its frequency grows with each selection.
    suggestionsManager.recordWord(suggestion)
    suggestionsManager.clearSuggestions()
}
