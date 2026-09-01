package inc.flide.vim8.app.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inc.flide.vim8.R
import inc.flide.vim8.lib.compose.Screen
import inc.flide.vim8.lib.compose.stringRes

@Composable
fun TutorialScreen() = Screen {
    title = stringRes(R.string.tutorial__title)
    previewFieldVisible = true

    content {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TutorialCard(
                title = "تعويذ واري ڪيبورڊ سان لکڻ جو طريقو",
                description = "هيءُ ڪيبورڊ اشارن (Gestures) ذريعي تيز لکڻ لاءِ آهي. هر اکر ٽائپ ڪرڻ لاءِ مرڪزي گول دائري مان شروع ڪري، اکر ڏانهن وڃي واپس مرڪز ۾ اچبو آهي."
            )

            TutorialCard(
                title = "1. تڪڙا اشارا (Quick Gestures)",
                description = """
                    • مرڪز ← هيٺ ← مرڪز : تڪڙو شفٽ آن يا آف (Quick Shift)
                    • مرڪز ← مٿي ← مرڪز : وچ وارو تجويز ڪيل لفظ چونڊيو
                    • مرڪز ← کاٻي ← مرڪز : کاٻي پاسي وارو لفظ چونڊيو
                    • مرڪز ← ساڄي ← مرڪز : ساڄي پاسي وارو لفظ چونڊيو
                """.trimIndent()
            )

            TutorialCard(
                title = "2. شفٽ اکر ۽ اعرابون (Shift Layer)",
                description = "هر اکر جي ڀرسان هلڪي رنگ ۾ ان جو شفٽ اکر يا اعراب ڏنل آهي. آڱر گهمائڻ دوران مٿين پٽي تي به شفٽ اکر ظاهر ٿيندو. شفٽ اکر لکڻ لاءِ سڄو چڪر گهمايو يا تڪڙو اشارو (مرڪز ← هيٺ ← مرڪز) استعمال ڪريو."
            )

            TutorialCard(
                title = "3. مرڪز تي ڊبل ٽيپ سان فل اسٽاپ",
                description = "مرڪزي گول دائري تي تيزيءَ سان ٻه ڀيرا ٽيپ ڪرڻ سان پاڻمرادو فل اسٽاپ (.) لکجي ويندو."
            )

            TutorialCard(
                title = "4. ٻولي بدلائڻ (Language Switching)",
                description = "سائيڊ بار تي (🌐) بٽڻ دٻائڻ سان اوهان سنڌي ۽ انگريزي (English) وچ ۾ هڪدم سوئچ ڪري سگهو ٿا."
            )
        }
    }
}

@Composable
private fun TutorialCard(title: String, description: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                textAlign = TextAlign.Right,
                lineHeight = 22.sp
            )
        }
    }
}
