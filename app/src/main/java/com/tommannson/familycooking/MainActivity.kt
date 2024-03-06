package com.tommannson.familycooking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.tommannson.familycooking.infrastructure.InfraProvider
import com.tommannson.familycooking.ui.navigation.LocalRootNavigator
import com.tommannson.familycooking.ui.navigation.NavigationHolder
import com.tommannson.familycooking.ui.screens.recipe.list.ListScreen
import com.tommannson.paymentorganizer.theme.new.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var infrastructure: InfraProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        infrastructure.register(this)

        setContent {
            NavigationHolder {
                AppTheme(
                    dynamicColor = false
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .navigationBarsPadding(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Navigator(
                            screen = ListScreen()
                        ) { navigator ->
                            LocalRootNavigator.current
                                .configure(navigator)
                            CurrentScreen()
                        }
                    }

//                    SearchScreen()


                }
            }
        }
    }
}