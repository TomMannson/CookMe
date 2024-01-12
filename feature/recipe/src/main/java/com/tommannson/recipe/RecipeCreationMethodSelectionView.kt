package com.tommannson.recipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tommannson.paymentorganizer.components.texfield.AppTextField

@Composable
fun RecipeCreationMethodSelectionView(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxSize()
            .padding(32.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(text = "Wybierze metodę tworzenia przepisu.\nMożesz dodać przepis ręcznie albo skorzystać z opcji wczytywania przepisu z obrazka")
        Spacer(modifier = Modifier.height(64.dp))

        Column {
            Button(
                onClick = { /*TODO*/ }
            ) {
                Text(text = "Wprowadź przepis ręcznie")
            }
            Button(
                onClick = { /*TODO*/ }
            ) {
                Text(text = "Zeskanuj przepis automatycznie")
            }
        }
    }
}

@Preview
@Composable
private fun RecipeCreationMethodSelectionViewPreview() {
    RecipeCreationMethodSelectionView()
}