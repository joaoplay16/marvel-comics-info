package com.appdate.marvelcomicsinfo

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.appdate.marvelcomicsinfo.ui.theme.MarvelComicsInfoTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private lateinit var myContext: Context
    val comicsViewModel by viewModels<ComicsViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myContext = this
        setContent {
            MarvelComicsInfoTheme {
                // A surface container using the 'background' color from the theme
                   Surface(
                       modifier = Modifier.fillMaxSize(),
                       color = MaterialTheme.colors.surface
                   ) {
//                SingleComicScreen()
                       ComicsActivityScreen()
                   }
               }
        }

    }

    @Composable
    fun ComicsActivityScreen() {
        ComicsScreen(comicsViewModel)
    }
}




