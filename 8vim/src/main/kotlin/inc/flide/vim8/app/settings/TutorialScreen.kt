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
                title = "ڦيٿي ذريعي لکڻ جو طريقو",
                description = "سنڌي ڦيٿو ڪيبورڊ اشارن ذريعي تيز رفتار لکڻ لاءِ تيار ڪيو ويو آهي. هر اکر ٽائپ ڪرڻ لاءِ مرڪزي گول دائري مان شروع ڪري واپس مرڪز ۾ اچڻو پوندو آهي."
            )

            TutorialCard(
                title = "1. پهرين ليول جا اهم ترين اکر",
                description = "سنڌي ٻوليءَ جا 8 سڀ کان وڌيڪ استعمال ٿيندڙ اکر (ي، ا، ن، و، ر، ھ، م، ج) مرڪزي دائري جي ويجهو آهن. انهن کي لکڻ لاءِ آڱر مرڪز کان ٻاهر ڪڍي سڌو واپس مرڪز ۾ آڻيو."
            )

            TutorialCard(
                title = "2. چڪر ذريعي اکر لکڻ",
                description = "ٻئي، ٽئين ۽ چوٿين ليول جي اکرن لاءِ مرڪز مان نڪري ڦيٿي جي چؤڌاري گهمايو ۽ پوءِ مرڪز ۾ داخل ٿيو."
            )

            TutorialCard(
                title = "3. شفٽ اکر ۽ حرڪتون",
                description = "هر اکر جي ڀرسان هلڪي رنگ ۾ ان جو شفٽ اکر ڏنل آهي. شفٽ لاءِ پورو چڪر گهمايو يا شفٽ بٽڻ دٻايو. زبر، زير، پيش ۽ مقدس لفظ (اللّٰه، محمّد، ﷺ) شفٽ سان آساني سان لکي سگهجن ٿا."
            )

            TutorialCard(
                title = "4. لائيو اکر پٽي",
                description = "آڱر گهمائڻ دوران ڪيبورڊ جي مٿين پٽي تي گهربل اکر نمايان نظر ايندو."
            )

            TutorialCard(
                title = "5. ٻولي بدلائڻ",
                description = "سائيڊ بار تي ٻولي جي بٽڻ (🌐) کي دٻائي سنڌي ۽ انگريزي ۾ سوئچ ڪريو."
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
