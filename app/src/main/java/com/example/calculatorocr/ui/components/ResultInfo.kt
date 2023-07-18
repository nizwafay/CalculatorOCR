package com.example.calculatorocr.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.calculatorocr.R
import com.example.calculatorocr.domain.model.Response
import com.example.calculatorocr.ui.screen.ImageAnalyzingResultUiState

@Composable
fun ResultInfo(
    imageAnalyzingResultUiStateResponse: Response<ImageAnalyzingResultUiState>?,
    modifier: Modifier = Modifier
) {
    when (imageAnalyzingResultUiStateResponse) {
        is Response.Success -> ResultInfoContent(
            fullText = imageAnalyzingResultUiStateResponse.data?.fullText
                ?: stringResource(id = R.string.no_expression),
            firstExpression = imageAnalyzingResultUiStateResponse.data?.firstExpression
                ?: stringResource(
                    id = R.string.no_expression
                ),
            result = imageAnalyzingResultUiStateResponse.data?.result?.toString() ?: stringResource(
                id = R.string.no_expression
            ),
            modifier = modifier
        )

        is Response.Loading -> ResultInfoContent(
            fullText = stringResource(id = R.string.loading),
            firstExpression = stringResource(id = R.string.loading),
            result = stringResource(id = R.string.loading),
            modifier = modifier
        )

        is Response.Error -> ResultInfoContent(
            fullText = imageAnalyzingResultUiStateResponse.e.message
                ?: stringResource(id = R.string.error),
            firstExpression = stringResource(id = R.string.no_data),
            result = stringResource(id = R.string.no_data),
            modifier = modifier
        )

        else -> ResultInfoContent(
            fullText = stringResource(id = R.string.no_image_selected),
            firstExpression = stringResource(id = R.string.no_data),
            result = stringResource(id = R.string.no_data),
            modifier = modifier
        )
    }
}

@Composable
fun ResultInfoContent(
    fullText: String, firstExpression: String, result: String, modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.full_text_x, fullText),
        )
        Text(
            text = stringResource(id = R.string.first_expression_x, firstExpression),
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = stringResource(id = R.string.result_x, result),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}