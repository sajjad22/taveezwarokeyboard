package inc.flide.vim8.ime.nlp

import android.content.Context
import inc.flide.vim8.Vim8ImeService
import inc.flide.vim8.appPreferenceModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** Characters that signal the end of a word and the start of the next. */
internal val WORD_BOUNDARY_CHARS = setOf(' ', '.', ',')

/**
 * Manages word suggestions for the keyboard.
 */
class SuggestionsManager(
    context: Context,
    private val repository: WordFrequencyRepository = WordFrequencyRepository(context)
) {
    private val prefs by appPreferenceModel()
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _suggestions = MutableStateFlow<List<String>>(emptyList())
    val suggestions: StateFlow<List<String>> = _suggestions.asStateFlow()

    var currentWordLength: Int = 0
        private set

    private fun detectLanguage(sampleText: String): String {
        if (sampleText.any { it in '\u0600'..'\u06FF' || it in '\uFB50'..'\uFDFF' || it in '\uFE70'..'\uFEFF' }) {
            return "sd"
        }
        val layout = prefs.layout.current.get().path.toString()
        return if (layout.contains("sd")) "sd" else "en"
    }

    /**
     * Called whenever the text before the cursor changes.
     */
    fun onTextBeforeCursor(text: CharSequence) {
        val str = text.toString()
        if (str.isEmpty()) {
            _suggestions.value = emptyList()
            currentWordLength = 0
            return
        }

        val lang = detectLanguage(str)

        if (str.last() in WORD_BOUNDARY_CHARS) {
            currentWordLength = 0
            val completedWord = extractWordBeforeBoundary(str)
            if (completedWord.isNotEmpty()) {
                val wordLang = if (completedWord.any { it in '\u0600'..'\u06FF' || it in '\uFB50'..'\uFDFF' || it in '\uFE70'..'\uFEFF' }) "sd" else "en"
                scope.launch {
                    repository.recordWord(completedWord, wordLang)
                }
            }
            _suggestions.value = emptyList()
        } else {
            val currentWord = extractCurrentWord(str)
            currentWordLength = currentWord.length
            if (currentWord.isEmpty()) {
                _suggestions.value = emptyList()
                return
            }
            scope.launch {
                _suggestions.value = repository.getCompletions(currentWord, lang, MAX_SUGGESTIONS)
            }
        }
    }

    /**
     * Records that [word] was explicitly selected by the user.
     */
    fun recordWord(word: String) {
        val wordLang = if (word.any { it in '\u0600'..'\u06FF' || it in '\uFB50'..'\uFDFF' || it in '\uFE70'..'\uFEFF' }) "sd" else "en"
        scope.launch { repository.recordWord(word, wordLang) }
    }

    /**
     * Clears the current suggestions list and resets the word-length counter.
     */
    fun clearSuggestions() {
        _suggestions.value = emptyList()
        currentWordLength = 0
    }

    /**
     * Commits the suggestion displayed at the given visual slot via a gesture.
     */
    fun commitSuggestion(visualSlot: Int) {
        val rankIndex = when (visualSlot) {
            0 -> 2
            1 -> 0
            2 -> 1
            else -> return
        }
        val word = _suggestions.value.getOrNull(rankIndex) ?: return
        val ic = Vim8ImeService.currentInputConnection() ?: return
        if (currentWordLength > 0) {
            ic.deleteSurroundingText(currentWordLength, 0)
        }
        ic.commitText("$word ", 1)
        val wordLang = if (word.any { it in '\u0600'..'\u06FF' || it in '\uFB50'..'\uFDFF' || it in '\uFE70'..'\uFEFF' }) "sd" else "en"
        scope.launch { repository.recordWord(word, wordLang) }
        clearSuggestions()
    }

    /**
     * Cancels the background coroutine scope.
     */
    fun destroy() {
        scope.cancel()
    }

    internal fun extractCurrentWord(text: String): String {
        if (text.isEmpty()) return ""
        if (text.last().isWhitespace()) return ""
        val lastWhitespace = text.indexOfLast { it.isWhitespace() }
        return if (lastWhitespace == -1) text else text.substring(lastWhitespace + 1)
    }

    internal fun extractWordBeforeBoundary(text: String): String {
        val stripped = text.trimEnd { it in WORD_BOUNDARY_CHARS }
        if (stripped.isEmpty()) return ""
        val lastBoundary = stripped.indexOfLast { it in WORD_BOUNDARY_CHARS }
        return if (lastBoundary == -1) stripped else stripped.substring(lastBoundary + 1)
    }

    companion object {
        const val MAX_SUGGESTIONS = 3
    }
}
