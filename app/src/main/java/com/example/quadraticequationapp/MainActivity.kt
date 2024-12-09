package com.example.quadraticequationapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quadraticequationapp.ui.theme.QuadraticEquationAppTheme
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuadraticEquationAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    QuadraticApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun QuadraticApp(
    modifier: Modifier = Modifier,
) {
    // Variables to handle the coefficient entries
    var coefficientA by remember { mutableStateOf("") }
    var coefficientB by remember { mutableStateOf("") }
    var coefficientC by remember { mutableStateOf("") }
    // Handle the result
    var result by remember { mutableStateOf("")}
    // Keyboard controller for textFields
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.app_titlte),
                fontSize = 24.sp
            )
        }

        Column {
            // Coefficient A TextField
            CoefficientTextField(
                value = coefficientA,
                onValueChange = { coefficientA = it },
                label = { Text(stringResource(R.string.first_coefficient_a_textfield)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
            // Coefficient B TextField
            CoefficientTextField(
                value = coefficientB,
                onValueChange = { coefficientB = it },
                label = { Text(stringResource(R.string.second_coefficient_b_textfield)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
            // Coefficient C TextField
            CoefficientTextField(
                value = coefficientC,
                onValueChange = { coefficientC = it },
                label = { Text(stringResource(R.string.third_coefficient_c_textfield)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {keyboardController?.hide()})
            )
        }

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(R.string.quadratic_equation_label))
            Text(
                text = "$coefficientA x² + $coefficientB x + $coefficientC = 0"
            )
        }

        // Button to initiate the calculation
        Button(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = {
                result = quadraticFormula(
                    coefficientA.toDouble(),
                    coefficientB.toDouble(),
                    coefficientC.toDouble()
                )
            },
        ) {
            Text(text = stringResource(R.string.calculate_btn))
        }
        Text(
            text = stringResource(R.string.results_text),
            fontSize = 24.sp,
            modifier = modifier
                .padding(10.dp)
                )
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White, shape = RoundedCornerShape(10.dp))
                .padding(16.dp)
        ) {
            Text(
                text = result,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

// Function to set the TextView content
@Composable
fun CoefficientTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable () -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default

) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        value = value,
        onValueChange = onValueChange,
        label = label,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}


fun quadraticFormula(coefficientA: Double, coefficientB: Double, coefficientC: Double): String {
    val delta = deltaCalculation(coefficientA, coefficientB, coefficientC)

    val x1 = BigDecimal((-coefficientB + sqrt(delta)) / (2 * coefficientA))
        .setScale(2, RoundingMode.HALF_EVEN);
    val x2 = BigDecimal((-coefficientB - sqrt(delta)) / (2 * coefficientA))
        .setScale(2, RoundingMode.HALF_EVEN);


    return if (delta < 0.0) {
        "There is no solution to this equation."
    } else {
        "The solution to this equation is: \nx1 = $x1 \nx2 = $x2"
    }
}


fun deltaCalculation(coefficientA: Double, coefficientB: Double, coefficientC: Double): Double {
    return coefficientB.pow(2.0) - 4 * coefficientA * coefficientC
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    QuadraticEquationAppTheme {
        QuadraticApp()
    }
}