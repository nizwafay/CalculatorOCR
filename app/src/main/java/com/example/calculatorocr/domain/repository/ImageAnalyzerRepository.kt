package com.example.calculatorocr.domain.repository

import android.net.Uri
import com.example.calculatorocr.domain.model.Response

interface ImageAnalyzerRepository {
    suspend fun getTextFromImage(imageUri: Uri): Response<String>
}