package com.tommannson.familycooking.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tommannson.familycooking.infrastructure.LocalInfrastructureProvider
import com.tommannson.paymentorganizer.theme.CMTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

val resultFlow = MutableStateFlow("")

@Composable
fun App(modifier: Modifier = Modifier) {
    CMTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(state = rememberScrollState())
            ) {
                val scope = rememberCoroutineScope()
                val processedResult by resultFlow.collectAsState()
                Text("Załaduj przepis")
                val infra = LocalInfrastructureProvider.current
                Button(
                    onClick = {
                        Log.d("REQ", "START")
                        scope.launch {
                            Log.d("REQ", "START")
                            val uri = infra.launchCamera() ?: return@launch
                            Log.d("REQ", "URI loaded")
                            resultFlow.value = infra.processRecipe(uri) ?: "ERROR"
                        }
                    }
                ) {
                    Text(text = "Open Camera")
                }
                Spacer(modifier = Modifier.height(50.dp))
                Text(text = "Rezultat to:")
                Text(text = processedResult)

            }

//                    SearchScreen()

        }
    }
}