package com.lokewt.potd.ui.screens.proverb

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProverbScreen(
    viewModel: ProverbViewModel = viewModel()
) {
    val verse = viewModel.verse

    LaunchedEffect(Unit) {
        viewModel.loadVerse()
    }
    Column(modifier =
        Modifier.padding(12.dp, 24.dp, 12.dp).fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            Text(
                text = "Proverb Of The Day",
                fontSize = 35.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = verse?.text ?: "",
                fontSize = 30.sp,
                lineHeight = 1.3.em
            )
            Text(
                text = "Proverb ${verse?.chapter}:${verse?.verse}",
                fontSize = 18.sp,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Column() {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button({ -> viewModel.refreshVerse() }) {
                    Text(text = "Generate New", fontSize = 18.sp)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview()
@Composable
fun GreetingPreview() {
    ProverbScreen()
}
