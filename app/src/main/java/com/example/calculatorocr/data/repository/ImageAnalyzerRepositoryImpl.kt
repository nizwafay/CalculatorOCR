package com.example.calculatorocr.data.repository

import android.content.Context
import android.net.Uri
import com.example.calculatorocr.domain.model.Response
import com.example.calculatorocr.domain.repository.ImageAnalyzerRepository
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognizer
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageAnalyzerRepositoryImpl @Inject constructor(
    private val context: Context,
    private val recognizer: TextRecognizer
) : ImageAnalyzerRepository {
    override suspend fun getTextFromImage(imageUri: Uri): Response<String> {
        return try {
            val text = recognizer.process(InputImage.fromFilePath(context, imageUri)).await().text
            Response.Success(text)
        } catch (e: Exception) {
            Response.Error(e)
        }
    }
}