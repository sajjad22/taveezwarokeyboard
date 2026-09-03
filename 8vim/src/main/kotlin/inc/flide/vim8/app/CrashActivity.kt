package inc.flide.vim8.app

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast

class CrashActivity : Activity() {

    companion object {
        const val EXTRA_ERROR_LOG = "extra_error_log"

        fun start(context: Context, errorLog: String) {
            val intent = Intent(context, CrashActivity::class.java).apply {
                putExtra(EXTRA_ERROR_LOG, errorLog)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val errorLog = intent.getStringExtra(EXTRA_ERROR_LOG) ?: "No error details available."

        val rootLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 60, 40, 40)
            setBackgroundColor(Color.parseColor("#121212"))
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        val titleView = TextView(this).apply {
            text = "⚠️ Taveez Waro Keyboard Error Report"
            textSize = 18f
            setTypeface(null, Typeface.BOLD)
            setTextColor(Color.parseColor("#EF4444"))
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(0, 0, 0, 20)
        }
        rootLayout.addView(titleView)

        val descView = TextView(this).apply {
            text = "An unexpected error occurred. Tap below to copy the crash log and report it."
            textSize = 14f
            setTextColor(Color.parseColor("#CCCCCC"))
            setPadding(0, 0, 0, 30)
        }
        rootLayout.addView(descView)

        val copyButton = Button(this).apply {
            text = "📋 Copy Crash Log to Clipboard"
            setBackgroundColor(Color.parseColor("#0284C7"))
            setTextColor(Color.WHITE)
            textSize = 16f
            setPadding(20, 20, 20, 20)
            setOnClickListener {
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Crash Log", errorLog)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(this@CrashActivity, "✅ Crash log copied to clipboard!", Toast.LENGTH_LONG).show()
            }
        }
        rootLayout.addView(copyButton)

        val scrollView = ScrollView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1.0f
            ).apply {
                topMargin = 30
                bottomMargin = 20
            }
            setBackgroundColor(Color.parseColor("#1E1E1E"))
            setPadding(20, 20, 20, 20)
        }

        val logView = TextView(this).apply {
            text = errorLog
            textSize = 12f
            typeface = Typeface.MONOSPACE
            setTextColor(Color.parseColor("#FCA5A5"))
            setTextIsSelectable(true)
        }
        scrollView.addView(logView)
        rootLayout.addView(scrollView)

        setContentView(rootLayout)
    }
}
