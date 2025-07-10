package de.stefan.lang.shapebyte.android.shared.image.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import de.stefan.lang.foundationCore.api.assets.ImageAsset
import de.stefan.lang.foundationCore.api.image.Image
import de.stefan.lang.foundationCore.api.image.ImageResource
import de.stefan.lang.shapebyte.android.shared.resources.mapping.ImageMapper
import de.stefan.lang.shapebyte.android.utils.assets.assetsPath
import java.io.IOException

@Composable
fun AsyncImage(
    url: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    AsyncImage(
        model = url,
        modifier = modifier,
        contentDescription = contentDescription,
    )
}

@Composable
fun AsyncImage(
    image: Image?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    if (image == null) {
        return
    }

    when (image) {
        is ImageAsset -> ShowImageAsset(image, modifier, contentDescription)
        is ImageResource -> ShowImageResource(image, modifier, contentDescription)
    }
}

@Composable
private fun ShowImageAsset(
    asset: ImageAsset,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    if (LocalInspectionMode.current) {
        loadImageFromAssets(LocalContext.current, asset.subPath)?.let {
            Image(
                bitmap = it.asImageBitmap(),
                modifier = modifier,
                contentDescription = contentDescription,
            )
        }
    } else {
        AsyncImage(
            url = asset.assetsPath,
            modifier = modifier,
            contentDescription = contentDescription,
        )
    }
}

@Composable
private fun ShowImageResource(
    imageResource: ImageResource,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    val resId = ImageMapper.resIdFor(imageResource)
    Image(
        painter = painterResource(id = resId),
        modifier = modifier,
        contentDescription = contentDescription,
    )
}

private fun loadImageFromAssets(context: Context, imageName: String): Bitmap? {
    return try {
        val assetManager = context.assets
        val inputStream = assetManager.open(imageName)
        BitmapFactory.decodeStream(inputStream)
    } catch (_: IOException) {
        null
    }
}
