package com.example.calculatorocr.ui.screen

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.calculatorocr.BuildConfig
import com.example.calculatorocr.R
import com.example.calculatorocr.domain.model.Response
import com.example.calculatorocr.ui.components.CapturedImage
import com.example.calculatorocr.ui.components.ResultInfo
import com.example.calculatorocr.util.createImageFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Objects

@Composable
fun CalculatorScreen(
    modifier: Modifier = Modifier,
    flavor: String = BuildConfig.FLAVOR,
    viewModel: CalculatorViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    if (flavor.contains("filesystem")) {
        FileSystemCalculatorScreen(
            modifier = modifier,
            capturedImageUri = viewModel.capturedImageUri,
            imageRecognitionResult = viewModel.imageRecognitionResult,
            coroutineScope = coroutineScope,
            onImageCaptured = {
                viewModel.onImageCaptured(it)
            }
        )
    } else {
        CameraCalculatorScreen(
            modifier = modifier,
            capturedImageUri = viewModel.capturedImageUri,
            imageRecognitionResult = viewModel.imageRecognitionResult,
            coroutineScope = coroutineScope,
            onImageCaptured = {
                viewModel.onImageCaptured(it)
            }
        )
    }
}

@Composable
fun CameraCalculatorScreen(
    modifier: Modifier,
    capturedImageUri: Uri,
    imageRecognitionResult: Response<ImageAnalyzingResultUiState>?,
    coroutineScope: CoroutineScope,
    onImageCaptured: (Uri) -> Unit
) {
    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        BuildConfig.APPLICATION_ID + ".provider",
        file
    )

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        coroutineScope.launch {
            onImageCaptured(uri)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(
                context,
                context.resources.getText(R.string.permission_granted),
                Toast.LENGTH_SHORT
            ).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(
                context,
                context.resources.getText(R.string.permission_denied),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    CalculatorScreenContent(
        modifier = modifier,
        capturedImageUri = capturedImageUri,
        imageRecognitionResult = imageRecognitionResult,
        onGetPhotoButtonClicked = {
            val permissionCheckResult =
                ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                cameraLauncher.launch(uri)
            } else {
                // Request a permission
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    )
}

@Composable
fun FileSystemCalculatorScreen(
    modifier: Modifier,
    capturedImageUri: Uri,
    imageRecognitionResult: Response<ImageAnalyzingResultUiState>?,
    coroutineScope: CoroutineScope,
    onImageCaptured: (Uri) -> Unit
) {
    val imageExplorerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            it?.let {
                coroutineScope.launch {
                    onImageCaptured(it)
                }
            }
        }

    CalculatorScreenContent(
        modifier = modifier,
        capturedImageUri = capturedImageUri,
        imageRecognitionResult = imageRecognitionResult,
        onGetPhotoButtonClicked = {
            imageExplorerLauncher.launch("image/*")
        }
    )
}

@Composable
fun CalculatorScreenContent(
    modifier: Modifier,
    capturedImageUri: Uri,
    imageRecognitionResult: Response<ImageAnalyzingResultUiState>?,
    onGetPhotoButtonClicked: () -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        CapturedImage(
            imageUri = capturedImageUri,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            ResultInfo(
                imageAnalyzingResultUiStateResponse = imageRecognitionResult
            )
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = { onGetPhotoButtonClicked() }
        ) {
            Text(text = stringResource(id = R.string.get_photo))
        }
    }
}