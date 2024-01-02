package com.example.androidsamples.ui.screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidsamples.Screen
import com.example.androidsamples.activity.RichEditTextActivity
import com.example.androidsamples.viewmodel.MainViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LazyColumn(
        modifier = modifier,
    ) {
        items(uiState.screens) {
            Column(
                modifier
                    .clickable(onClick = { click(context, it) })
                    .padding(start = 4.dp, end = 4.dp, top = 8.dp, bottom = 8.dp)
            )
            {
                Text(
                    text = it.name,
                    modifier = modifier
                )
            }
            Divider()
        }
    }
}

fun click(context: Context, screen: Screen) {
    when (screen) {
        Screen.RichEditText -> context.startActivity(
            Intent(
                context,
                RichEditTextActivity::class.java
            )
        )
    }
}
