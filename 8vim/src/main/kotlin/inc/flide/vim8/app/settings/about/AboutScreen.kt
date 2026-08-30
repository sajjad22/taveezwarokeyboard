package inc.flide.vim8.app.settings.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inc.flide.vim8.R
import inc.flide.vim8.app.LocalNavController
import inc.flide.vim8.app.Routes
import inc.flide.vim8.app.Urls
import inc.flide.vim8.datastore.ui.Preference
import inc.flide.vim8.lib.android.launchUrl
import inc.flide.vim8.lib.compose.AppIcon
import inc.flide.vim8.lib.compose.Screen
import inc.flide.vim8.lib.compose.stringRes

@Composable
fun AboutScreen() = Screen {
    title = stringRes(R.string.about__title)

    val context = LocalContext.current
    val navController = LocalNavController.current

    content {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 24.dp)
        ) {
            AppIcon()
            Text(
                text = stringRes(R.string.app_name),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Text(
                text = "هيءُ ڪيبورڊ 8Vim ڪيبورڊ مان متاثر ٿي فورڪ ڪيو ويو آهي، جيڪو اصل ۾ 8Pen جي تصور تي ٻڌل هو ۽ سنڌي ٻوليءَ لاءِ ترتيب ڏنو ويو آهي.",
                fontSize = 15.sp,
                textAlign = TextAlign.Right,
                lineHeight = 24.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Preference(
            iconId = R.drawable.ic_error_outline,
            title = stringRes(R.string.app__version__label),
            summary = stringRes(R.string.version_name)
        )
        HorizontalDivider()
        Preference(
            iconId = R.drawable.twitter_vd_vector,
            title = stringRes(R.string.settings__about__twitter__label),
            onClick = { context.launchUrl(Urls.TWITTER) }
        )
        HorizontalDivider()
        Preference(
            iconId = R.drawable.ic_description,
            title = stringRes(R.string.about__third_party_licenses__title),
            summary = stringRes(R.string.about__third_party_licenses__summary),
            onClick = { navController.navigate(Routes.Settings.THIRD_PARTY_LICENSES) }
        )
    }
}
