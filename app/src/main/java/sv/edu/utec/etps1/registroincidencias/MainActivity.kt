package sv.edu.utec.etps1.registroincidencias

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import sv.edu.utec.etps1.registroincidencias.ui.theme.RegistroIncidenciasTheme
import kotlin.math.sqrt
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll


class MainActivity : ComponentActivity() {

    private val tag = "REGISTRO_LIFECYCLE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(tag, "onCreate")

        val incidenciaViewModel =
            ViewModelProvider(this)[IncidenciaViewModel::class.java]

        setContent {
            RegistroIncidenciasTheme {
                RegistroIncidenciasApp(
                    vm = incidenciaViewModel
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "onResume")
    }

    override fun onPause() {
        Log.d(tag, "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d(tag, "onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d(tag, "onDestroy")
        super.onDestroy()
    }
}


@Composable
fun RegistroIncidenciasApp(
    vm: IncidenciaViewModel
) {

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

    var estadoMovimiento by remember {
        mutableStateOf("Dispositivo estable")
    }

    var ultimoMovimiento by remember {
        mutableStateOf(0L)
    }


    DisposableEffect(accelerometer) {

        val listener = object : SensorEventListener {

            override fun onSensorChanged(event: SensorEvent) {

                ejeX = event.values[0]
                ejeY = event.values[1]
                ejeZ = event.values[2]

                val magnitud = sqrt(
                    (ejeX * ejeX + ejeY * ejeY + ejeZ * ejeZ).toDouble()
                ).toFloat()

                val ahora = System.currentTimeMillis()

                if (magnitud > 12f) {

                    ultimoMovimiento = ahora
                    estadoMovimiento = "Movimiento detectado"

                } else if (ahora - ultimoMovimiento > 1500) {

                    estadoMovimiento = "Dispositivo estable"
                }
            }

            override fun onAccuracyChanged(
                sensor: Sensor?,
                accuracy: Int
            ) {
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


    var descripcion by remember {
        mutableStateOf("")
    }

    var mensaje by remember {
        mutableStateOf("Aun no hay reporte Creado")
    }

    var esError by remember {
        mutableStateOf(false)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Registro de Incidencias",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "Completa los Datos Básicos para Registrar el Problema."
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )


        OutlinedTextField(
            value = vm.titulo,

            onValueChange = { nuevoTexto ->
                vm.actualizarTitulo(nuevoTexto)
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


        Spacer(
            modifier = Modifier.height(16.dp)
        )


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


        Spacer(
            modifier = Modifier.height(16.dp)
        )


        Text(
            text = "Prioridad: ${vm.prioridad}",
            fontWeight = FontWeight.Bold
        )


        Text(
            text = "Toca aquí para cambiar la prioridad",

            modifier = Modifier
                .padding(top = 8.dp)
                .clickable {
                    vm.cambiarPrioridad()
                }
        )


        Text(
            text = "Diagnóstico del dispositivo",
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


        Text(
            text = "X: $ejeX"
        )

        Text(
            text = "Y: $ejeY"
        )

        Text(
            text = "Z: $ejeZ"
        )


        Text(
            text = "Estado: $estadoMovimiento",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp)
        )


        Spacer(
            modifier = Modifier.height(24.dp)
        )


        Button(
            onClick = {

                if (
                    vm.titulo.isBlank() ||
                    descripcion.isBlank()
                ) {

                    mensaje = "Completa los campos"
                    esError = true

                } else {

                    mensaje =
                        "Reporte Preparado: ${vm.titulo} - Prioridad: ${vm.prioridad}"

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


        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Incidencias Registradas",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = mensaje,

                    color = if (esError) {
                        Color.Red
                    } else {
                        Color.Unspecified
                    },

                    fontWeight = if (esError) {
                        FontWeight.Bold
                    } else {
                        FontWeight.Normal
                    }
                )
            }
        }


        Spacer(
            modifier = Modifier.height(12.dp)
        )


        Text(
            text = "Prototipo Inicial - Unidad I"
        )
    }
}


@Preview(showBackground = true)
@Composable
fun RegistroIncidenciasPreview() {

    RegistroIncidenciasTheme {

        RegistroIncidenciasApp(
            vm = IncidenciaViewModel()
        )
    }
}