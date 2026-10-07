package sv.edu.utec.etps1.registroincidencias


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sv.edu.utec.etps1.registroincidencias.ui.theme.RegistroIncidenciasTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.foundation.clickable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import androidx.compose.runtime.DisposableEffect

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegistroIncidenciasTheme {
                RegistroIncidenciasApp()
            }
        }
    }
}

@Composable
fun RegistroIncidenciasApp() {
    val context = LocalContext.current

    val sensorManager = remember {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    val accelerometer = remember {
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    var ejeX by remember { mutableStateOf(0f) }
    var ejeY by remember { mutableStateOf(0f) }
    var ejeZ by remember { mutableStateOf(0f) }

    DisposableEffect(accelerometer) {
        var listener = object : SensorEventListener{
            override fun onSensorChanged(event: SensorEvent) {
                ejeX = event.values[0]
                ejeY = event.values[1]
                ejeZ = event.values[2]
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

            }
        }

        if (accelerometer != null) {
            sensorManager.registerListener(
                listener,
                accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    // Se utiliza para reemplazar el texto mostrado por el nuevo
    // Digitado por el usuario
    var titulo by remember {
        mutableStateOf("")
    }

    var descripcion by remember{
        mutableStateOf("")
    }

    var mensaje by remember {
        mutableStateOf(  "Aun no hay reporte Creado")
    }

    var esError by remember {
        mutableStateOf(false)
    }

    var prioridad by remember {
        mutableStateOf("Media")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Registro de Incidencias",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Completa los Datos Básicos para Registrar el Problema."
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = titulo,
            onValueChange = { nuevoTexto ->
                titulo = nuevoTexto
            },
            label = {
                Text(
                    text = "Título de la incidencia"
                )
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = descripcion,
            onValueChange = { nuevoTexto ->
                descripcion = nuevoTexto
            },
            label = {
                Text(
                    text = "Descripción del Problema."
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Prioridad: $prioridad",
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Toca aquí para cambiar la prioridad",
            modifier = Modifier
                .padding(top = 8.dp)
                .clickable {
                    prioridad = when (prioridad) {
                        "Baja" -> "Media"
                        "Media" -> "Alta"
                        else -> "Baja"
                    }
                }
        )

        Text(
            text = "Sensor de movimiento",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = if (accelerometer != null) {
                "Acelerómetro activo"
            } else {
                "Acelerómetro no disponible"
            }
        )

        Text(text = "X: $ejeX")
        Text(text = "Y: $ejeY")
        Text(text = "Z: $ejeZ")

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = {
                if( titulo.isBlank() || descripcion.isBlank() ){
                    mensaje = "Completa los campos"
                    esError = true
                }else{
                    mensaje = "Reporte Preparado: $titulo - Prioridad: $prioridad"
                    esError = false
                }
            }
        ) {
            Text(
                text = "Registrar Incidencia"
            )
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Incidencias Registradas",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = mensaje,
                    color = if(esError) Color.Red else Color.Unspecified,
                    fontWeight = if ( esError ) FontWeight.Bold else FontWeight.Normal
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Prototipo Inicial - Unidad I"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegistroIncidenciasPreview() {
    RegistroIncidenciasTheme {
        RegistroIncidenciasApp()
    }
}