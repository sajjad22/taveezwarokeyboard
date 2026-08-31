package inc.flide.vim8.ime.nlp

import android.content.ContentValues
import android.content.Context
import android.content.res.AssetManager
import android.database.sqlite.SQLiteDatabase

/**
 * Repository for the private per-app word-frequency database.
 *
 * All public methods are **blocking** and must be called from a background thread (e.g.
 * inside a coroutine launched with [kotlinx.coroutines.Dispatchers.IO]).
 */
class WordFrequencyRepository(private val context: Context) {

    private val db by lazy { WordFrequencyDatabase(context) }

    /**
     * Returns up to [limit] words matching [prefix] for the given [lang], ordered by frequency descending.
     */
    fun getCompletions(prefix: String, lang: String = "sd", limit: Int = 3): List<String> {
        if (prefix.isBlank()) return emptyList()
        val cursor = db.readableDatabase.query(
            WF_TABLE,
            arrayOf(WF_COL_WORD),
            "$WF_COL_LANG = ? AND $WF_COL_WORD LIKE ?",
            arrayOf(lang, "${prefix.lowercase()}%"),
            null,
            null,
            "$WF_COL_FREQ DESC",
            limit.toString()
        )
        return cursor.use { c ->
            buildList { while (c.moveToNext()) add(c.getString(0)) }
        }
    }

    /**
     * Returns the top [limit] most-frequently-used words globally for the given [lang].
     */
    fun getTopWords(lang: String = "sd", limit: Int = 3): List<String> {
        val cursor = db.readableDatabase.query(
            WF_TABLE,
            arrayOf(WF_COL_WORD),
            "$WF_COL_LANG = ?",
            arrayOf(lang),
            null,
            null,
            "$WF_COL_FREQ DESC",
            limit.toString()
        )
        return cursor.use { c ->
            buildList { while (c.moveToNext()) add(c.getString(0)) }
        }
    }

    /**
     * Records that [word] was used for [lang].
     */
    fun recordWord(word: String, lang: String = "sd") {
        val lower = word.lowercase().trim()
        if (lower.isBlank()) return

        val wdb = db.writableDatabase
        val cv = ContentValues().apply {
            put(WF_COL_WORD, lower)
            put(WF_COL_LANG, lang)
            put(WF_COL_FREQ, 1)
        }
        val rowId = wdb.insertWithOnConflict(WF_TABLE, null, cv, SQLiteDatabase.CONFLICT_IGNORE)
        if (rowId == -1L) {
            wdb.execSQL(
                "UPDATE $WF_TABLE SET $WF_COL_FREQ = $WF_COL_FREQ + 1 WHERE $WF_COL_WORD = ? AND $WF_COL_LANG = ?",
                arrayOf(lower, lang)
            )
        }
    }

    /**
     * Seeds the database from assets if empty.
     */
    fun seedIfNeeded(assetManager: AssetManager) {
        val wdb = db.writableDatabase
        val count = wdb
            .compileStatement("SELECT COUNT(*) FROM $WF_TABLE")
            .simpleQueryForLong()
        if (count > 0L) return

        wdb.beginTransaction()
        try {
            seedFile(wdb, assetManager, "word_seed_sd.csv", "sd")
            seedFile(wdb, assetManager, "word_seed_en.csv", "en")
            wdb.setTransactionSuccessful()
        } finally {
            wdb.endTransaction()
        }
    }

    private fun seedFile(wdb: SQLiteDatabase, assetManager: AssetManager, fileName: String, lang: String) {
        try {
            val lines = assetManager.open(fileName).bufferedReader().readLines()
            for (line in lines) {
                val parts = line.split(",")
                if (parts.size < 2) continue
                val w = parts[0].trim().lowercase()
                val f = parts[1].trim().toIntOrNull() ?: continue
                if (w.isBlank()) continue
                val cv = ContentValues().apply {
                    put(WF_COL_WORD, w)
                    put(WF_COL_LANG, lang)
                    put(WF_COL_FREQ, f)
                }
                wdb.insertWithOnConflict(WF_TABLE, null, cv, SQLiteDatabase.CONFLICT_IGNORE)
            }
        } catch (_: Exception) {
            // File might not exist in some tests
        }
    }
}
