package com.example.calculatorocr.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            Box(
                Modifier
                    .size(300.dp)
                    .background(Color.Blue)
                    .align(Alignment.CenterHorizontally)
            )
            Text(text = "Text: 1+2+3", Modifier.padding(top = 32.dp))
            Text(text = "Result: 6", Modifier.padding(top = 8.dp))
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            onClick = { /*TODO*/ }
        ) {
            Text(text = "Get photo")
        }
    }
}

@Preview
@Composable
private fun CalculatorScreenPreview() {
    CalculatorScreen()
}