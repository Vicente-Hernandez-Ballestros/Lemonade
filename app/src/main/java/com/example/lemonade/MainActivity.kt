package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                LemonApp()
            }
        }
    }
}


@Composable
fun LemonApp() {

    // paso para mostrar la aplicacion
    // el remember es para conservar el estado
    var currentStep by remember { mutableStateOf(1) }

    // guarda cuantas vecs se tiene que tocar el limon para hacer limonada
    var squeezeCount by remember { mutableStateOf(0) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        when (currentStep) {
            1 -> {
                // Muestre la imagen del árbol de limón
                LemonTextAndImage(
                    textLabelResourceId = R.string.arbol_limon,
                    drawableResourceId = R.drawable.lemon_tree,
                    contentDescriptionResourceId = R.string.lemon_tree_content_description,
                    onImageClick = {
                        // le aumentamos un para que muestre la siguiente imagen
                        currentStep = 2
                        // generamos un ramdom entre 2 y 4 pra saber cuantas veces tenemos que tocar
                        // el limon pra hacer la limonada
                        squeezeCount = (2..4).random()
                    }
                )
            }
            2 -> {
                // Mostramos la imagen del limon cortado
                LemonTextAndImage(
                    textLabelResourceId = R.string.limon_cortado,
                    drawableResourceId = R.drawable.lemon_squeeze,
                    contentDescriptionResourceId = R.string.lemon_content_description,
                    onImageClick = {
                        // Decreetamos la variable de los toques al limon
                        squeezeCount--
                        // Verificamos que los taps o toques al limon llegue a 0
                        // cuando sea verdadero pasamos a la siguente imagen
                        if (squeezeCount == 0) {
                            currentStep = 3
                        }
                    }
                )
            }
            3 -> {
                // Muestra el vaso de limonada
                LemonTextAndImage(
                    textLabelResourceId = R.string.vaso_limonada,
                    drawableResourceId = R.drawable.lemon_drink,
                    contentDescriptionResourceId = R.string.lemonade_content_description,
                    onImageClick = {
                        // Nos la chingamos y bpasamos al siguiente paso
                        currentStep = 4
                    }
                )
            }
            4 -> {
                // Muestra el vaso vacio
                LemonTextAndImage(
                    textLabelResourceId = R.string.vaso_vacio,
                    drawableResourceId = R.drawable.lemon_restart,
                    contentDescriptionResourceId = R.string.empty_glass_content_description,
                    onImageClick = {
                        // Le decimos a la variable que vamos a empezar de nuevo
                        currentStep = 1
                    }
                )
            }
        }
    }
}

@Composable
fun LemonTextAndImage(
    textLabelResourceId: Int,
    drawableResourceId: Int,
    contentDescriptionResourceId: Int,
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(textLabelResourceId),
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(drawableResourceId),
            contentDescription = stringResource(contentDescriptionResourceId),
            modifier = Modifier
                .wrapContentSize()
                .clickable(
                    onClick = onImageClick
                )
                .border(
                    BorderStroke(2.dp, Color(105, 205, 216)),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(16.dp)
        )
    }
}

@Preview
@Composable
fun LemonPreview() {
    LemonadeTheme() {
        LemonApp()
    }
}