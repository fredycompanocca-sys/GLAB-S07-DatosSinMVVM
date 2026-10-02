package com.example.datossinmvvm

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.room.Room
import kotlinx.coroutines.launch
import androidx.compose.runtime.LaunchedEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenMovimiento() {
    val context = LocalContext.current
    val db = crearDatabase(context)
    val dao = db.movimientoDao()
    val coroutineScope = rememberCoroutineScope()

    var editId by remember { mutableStateOf(0) }
    var producto by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var lista by remember { mutableStateOf(listOf<Movimiento>()) }

    LaunchedEffect(Unit) {
        lista = dao.getAll()
    }
    fun refrescar() {
        coroutineScope.launch {
            lista = dao.getAll()
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Registro de Movimientos") }) },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                TextField(
                    value = producto,
                    onValueChange = { producto = it },
                    label = { Text("Producto") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = tipo,
                    onValueChange = { tipo = it },
                    label = { Text("Tipo (Entrada/Salida)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = cantidad,
                    onValueChange = { cantidad = it },
                    label = { Text("Cantidad") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = {
                        val cantidadInt = cantidad.toIntOrNull() ?: 0
                        coroutineScope.launch {
                            if (editId == 0) {
                                // Create
                                dao.insert(Movimiento(0, producto, tipo, cantidadInt))
                            } else {
                                // Update
                                dao.update(Movimiento(editId, producto, tipo, cantidadInt))
                                editId = 0
                            }
                            producto = ""
                            tipo = ""
                            cantidad = ""
                            refrescar()
                        }
                    }
                ) {
                    Text(if (editId == 0) "Registrar Movimiento" else "Guardar Cambios")
                }

                Spacer(Modifier.height(16.dp))


                LazyColumn {
                    items(lista) { mov ->
                        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("${mov.producto} (${mov.tipo})")
                                    Text("Cantidad: ${mov.cantidad}")
                                }

                                IconButton(onClick = {
                                    editId = mov.id
                                    producto = mov.producto ?: ""
                                    tipo = mov.tipo ?: ""
                                    cantidad = mov.cantidad.toString()
                                }) {
                                    Icon(Icons.Filled.Edit, contentDescription = "Editar")
                                }

                                IconButton(onClick = {
                                    coroutineScope.launch {
                                        try {
                                            dao.delete(mov)
                                        } catch (e: Exception) {
                                            Log.e("Movimiento", "Error: delete: ${e.message}")
                                        }
                                        refrescar()
                                    }
                                }) {
                                    Icon(Icons.Filled.Delete, contentDescription = "Eliminar")
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}