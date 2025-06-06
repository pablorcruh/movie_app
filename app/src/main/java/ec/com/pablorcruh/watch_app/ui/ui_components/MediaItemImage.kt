package ec.com.pablorcruh.watch_app.ui.ui_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import ec.com.pablorcruh.watch_app.main.data.remote.api.MediaApi
import ec.com.pablorcruh.watch_app.main.domain.model.Media
import ec.com.pablorcruh.watch_app.ui.theme.Radius

@Composable
fun MediaItemImage(
    modifier: Modifier = Modifier,
    media: Media,
    isPoster: Boolean = true,
    mainNavController: NavController
){

    val imageUri = if(isPoster){
        "${MediaApi.IMAGE_BASE_URL}${media.posterPath}"
    }else{
        "${MediaApi.IMAGE_BASE_URL}${media.backdropPath}"
    }

    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUri)
            .size(Size.ORIGINAL)
            .build()
    ).state

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(Radius))
            .background(MaterialTheme.colorScheme.inverseSurface)
            .clickable {

            }
    ) {
        when(imageState){
            is AsyncImagePainter.State.Success -> {
                Image(
                    painter = imageState.painter,
                    contentDescription = media.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            is AsyncImagePainter.State.Loading -> {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.Center)
                )
            }
            else -> {
                Icon(
                    imageVector = Icons.Rounded.Warning,
                    contentDescription = media.title,
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }

}