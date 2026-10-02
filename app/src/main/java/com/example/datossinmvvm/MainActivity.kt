package com.example.datossinmvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                var pantalla by remember { mutableStateOf("usuarios") }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding() // empuja el contenido debajo del notch/status bar
                ) {
                    Surface(
                        tonalElevation = 2.dp, // le da un leve relieve visual a la barra de botones
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp, horizontal = 16.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(onClick = { pantalla = "usuarios" }) {
                                Text("Usuarios")
                            }
                            Spacer(Modifier.width(16.dp))
                            Button(onClick = { pantalla = "movimientos" }) {
                                Text("Movimientos")
                            }
                        }
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        if (pantalla == "usuarios") {
                            ScreenUser()
                        } else {
                            ScreenMovimiento()
                        }
                    }
                }
            }
        }
    }
}