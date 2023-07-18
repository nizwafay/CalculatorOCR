package com.example.calculatorocr.ui.components

import android.net.Uri
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.calculatorocr.R

@Composable
fun CapturedImage(
    modifier: Modifier = Modifier,
    imageUri: Uri
) {
    AsyncImage(
        model = imageUri,
        contentDescription = stringResource(id = R.string.captured_image),
        modifier = modifier.size(300.dp)
    )
}