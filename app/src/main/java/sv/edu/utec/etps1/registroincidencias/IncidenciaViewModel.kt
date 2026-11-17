package sv.edu.utec.etps1.registroincidencias

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class IncidenciaViewModel : ViewModel() {

    var titulo by mutableStateOf("")
        private set

    var prioridad by mutableStateOf("Baja")
        private set

    fun actualizarTitulo(valor: String) {
        titulo = valor
    }

    fun cambiarPrioridad() {
        prioridad = when (prioridad) {
            "Baja" -> "Media"
            "Media" -> "Alta"
            else -> "Baja"
        }
    }
}