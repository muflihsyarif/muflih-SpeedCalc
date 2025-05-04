package com.muflihsyarif0023.speedcalc.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.muflihsyarif0023.speedcalc.R
import com.muflihsyarif0023.speedcalc.ui.theme.SpeedCalcTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        ScreenContent(Modifier.padding(innerPadding))
    }
}

@Composable
fun ScreenContent(modifier: Modifier = Modifier) {
    val options = listOf(
        stringResource(id = R.string.s),  // Distance
        stringResource(id = R.string.v),  // Speed
        stringResource(id = R.string.t)   // Time
    )
    var selectedOption by remember { mutableStateOf(options[0]) }
    var firstInput by remember { mutableStateOf("") }
    var secondInput by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<String?>(null) }
    val resultTemplate = stringResource(id = R.string.result)

    val sLabel = stringResource(id = R.string.s)
    val vLabel = stringResource(id = R.string.v)
    val tLabel = stringResource(id = R.string.t)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.intro),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.choose),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                options.forEach { option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (option == selectedOption),
                                onClick = {
                                    selectedOption = option
                                    firstInput = ""
                                    secondInput = ""
                                    result = null
                                }
                            )
                            .padding(vertical = 8.dp)
                    ) {
                        RadioButton(
                            selected = (option == selectedOption),
                            onClick = {
                                selectedOption = option
                                firstInput = ""
                                secondInput = ""
                                result = null
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = option)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val (firstLabel, secondLabel) = when (selectedOption) {
            sLabel -> stringResource(id = R.string.speed) to stringResource(id = R.string.time)
            vLabel -> stringResource(id = R.string.distance) to stringResource(id = R.string.time)
            tLabel -> stringResource(id = R.string.distance) to stringResource(id = R.string.speed)
            else -> "" to ""
        }

        OutlinedTextField(
            value = firstInput,
            onValueChange = { firstInput = it },
            label = { Text(firstLabel) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Text(
                    text = getUnitFromLabel(firstLabel),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = secondInput,
            onValueChange = { secondInput = it },
            label = { Text(secondLabel) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Text(
                    text = getUnitFromLabel(secondLabel),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val first = firstInput.toFloatOrNull() ?: 0f
                val second = secondInput.toFloatOrNull() ?: 0f

                result = when (selectedOption) {
                    sLabel -> {
                        val hasil = first * second
                        String.format(resultTemplate, hasil)
                    }

                    vLabel -> {
                        val hasil = first / second
                        String.format(resultTemplate, hasil)
                    }

                    tLabel -> {
                        val hasil = first / second
                        String.format(resultTemplate, hasil)
                    }

                    else -> null
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.count))
        }

        Spacer(modifier = Modifier.height(16.dp))

        result?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun getUnitFromLabel(label: String): String {
    return when (label) {
        stringResource(R.string.distance) -> "m"
        stringResource(R.string.speed) -> "m/s"
        stringResource(R.string.time) -> "s"
        else -> ""
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun GreetingPreview() {
    SpeedCalcTheme {
        MainScreen()
    }
}