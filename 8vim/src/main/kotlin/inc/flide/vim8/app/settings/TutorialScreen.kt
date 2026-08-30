package inc.flide.vim8.app.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
                .verticalScroll(rememberScrollState())
        ) {
            TutorialCard(
                title = "ڦيٿي ذريعي لکڻ جو طريقو (Basic Principle)",
                description = "سنڌي ڦيٿو ڪيبورڊ اشارن (Gestures) ذريعي تيز رفتار لکڻ لاءِ تيار ڪيو ويو آهي. هر اکر ٽائپ ڪرڻ لاءِ مرڪزي گول دائري مان شروع ڪري واپس مرڪز ۾ اچڻو پوندو آهي."
            )

            TutorialCard(
                title = "1. پهرين ليول جا سڀ کان اهم اکر (Level 1)",
                description = "سنڌي ٻوليءَ جا سڀ کان وڌيڪ استعمال ٿيندڙ 8 اکر (ي، ا، ن، و، ر، ھ، م، ج) مرڪزي دائري جي تمام ويجهو آهن. انهن کي ٽائپ ڪرڻ لاءِ رڳو آڱر مرڪز کان ٻاهر ڪڍي سڌو واپس مرڪز ۾ آڻيو."
            )

            TutorialCard(
                title = "2. چڪر ذريعي اکر لکڻ (Rings 2, 3 & 4)",
                description = "ٻئي، ٽئين ۽ چوٿين ليول جي اکرن لاءِ مرڪز مان ٻاهر نڪري ڦيٿي جي چوڌاري گھمايو ۽ پوءِ مرڪز ۾ واپس داخل ٿيو."
            )

            TutorialCard(
                title = "3. شفٽ اکر ۽ حرڪتون (Shift & Airabs)",
                description = "هر اکر جي ڀرسان هلڪي رنگ (Faded) ۾ ان جو شفٽ اکر ظاهر آهي. شفٽ آن ڪرڻ لاءِ پورو گول چڪر گھمايو يا Shift بٽڻ دٻايو. زبر (َ)، زير (ِ)، پيش (ُ)، تشديد (ّ)، جزم (ْ) ۽ مقدس لفظ (اللّٰه، محمّد، ﷺ) شفٽ سان آساني سان لکي سگهجن ٿا."
            )

            TutorialCard(
                title = "4. لائيو پريوو پٽي (Live Letter Preview)",
                description = "جڏهن توهان آڱر سرڪائيندا ته ڪيبورڊ جي بلڪل مٿئين پٽي تي اهو اکر وڏي نموني نمايان ٿيندو جنهن سان بنا غلطي جي پڪ سان لکي سگهجي ٿو."
            )

            TutorialCard(
                title = "5. ٻولي ۽ ڪيبورڊ سوئچ (Language & Switcher)",
                description = "سائيڊ بار تي ٻولي واري بٽڻ (🌐) کي ٽيپ ڪري سڌو سنڌي ۽ انگريزي ۾ مٽايو، ۽ ٻين ڪيبورڊن تي وڃڻ لاءِ ڪيبورڊ بٽڻ کي دٻايو."
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
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp),
                lineHeight = 22.sp
            )
        }
    }
}
