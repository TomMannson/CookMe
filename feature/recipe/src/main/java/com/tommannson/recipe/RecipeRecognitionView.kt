package com.tommannson.recipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tommannson.paymentorganizer.components.texfield.AppTextField
import org.w3c.dom.Text

@Composable
fun RecipeRecognitionView(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxSize()
            .padding(32.dp)
            .verticalScroll(rememberScrollState())
    ) {

        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            minLines = 15,
            label = "Loaded recipe content",
            onValueChange = {}
        )

        Spacer(modifier = Modifier.height(64.dp))
        Text(text = "Poniżej znajduje się wczytany przepis w bardziej przystępnej formie")
        Spacer(modifier = Modifier.height(16.dp))
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            minLines = 1,
            label = "Nazwa przepisu",
            onValueChange = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            minLines = 5,
            label = "Treść przepisu",
            onValueChange = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            minLines = 2,
            label = "Znalezione składniki",
            onValueChange = {}
        )

//        if() // narzędzia potrzebne podnaczas przygotowywania

        Spacer(modifier = Modifier.height(16.dp))
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            minLines = 2,
            label = "Niezbędne narzędzia",
            onValueChange = {}
        )

        Spacer(modifier = Modifier.height(16.dp))
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            minLines = 2,
            label = "Przewidywany czas realizacji",
            onValueChange = {}
        )
    }
}

@Preview
@Composable
private fun RecipeRecognitionViewPreview() {
    RecipeRecognitionView()
}