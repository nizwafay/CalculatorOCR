package com.example.calculatorocr.di

import android.content.Context
import com.example.calculatorocr.data.repository.ImageAnalyzerRepositoryImpl
import com.example.calculatorocr.domain.repository.ImageAnalyzerRepository
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideTextRecognizer() = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    @Provides
    fun provideImageAnalyzerRepository(
        @ApplicationContext context: Context,
        recognizer: TextRecognizer
    ): ImageAnalyzerRepository = ImageAnalyzerRepositoryImpl(
        context, recognizer
    )
}