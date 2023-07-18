package com.example.calculatorocr.ui.screen

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculatorocr.domain.model.Response
import com.example.calculatorocr.domain.repository.ImageAnalyzerRepository
import com.example.calculatorocr.util.calculateTwoArgumentsOperation
import com.example.calculatorocr.util.findFirstExpression
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ImageAnalyzingResultUiState(
    val fullText: String, val firstExpression: String?, val result: Float?
)

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val imageAnalyzerRepository: ImageAnalyzerRepository
) : ViewModel() {
    var capturedImageUri: Uri by mutableStateOf(Uri.EMPTY)
        private set

    var imageRecognitionResult: Response<ImageAnalyzingResultUiState>? by mutableStateOf(null)
        private set

    fun onImageCaptured(imageUri: Uri) {
        capturedImageUri = imageUri
        processImage(imageUri)
    }

    private fun processImage(imageUri: Uri) {
        imageRecognitionResult = Response.Loading
        viewModelScope.launch {
            imageRecognitionResult =
                when (val response = imageAnalyzerRepository.getTextFromImage(imageUri)) {
                    is Response.Success -> {
                        response.data?.let { responseData ->
                            val firstExpression = findFirstExpression(responseData)

                            Response.Success(
                                ImageAnalyzingResultUiState(fullText = responseData,
                                    firstExpression = firstExpression,
                                    result = firstExpression?.let { expression ->
                                        calculateTwoArgumentsOperation(expression)
                                    })
                            )
                        } ?: Response.Success(null)
                    }

                    is Response.Error -> {
                        Response.Error(response.e)
                    }

                    else -> Response.Loading
                }
        }
    }
}