package rs.ac.pr.ftn.akaticiksoks

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Lista koja sadrži svih 9 tekstualnih polja
    private lateinit var fields: List<TextView>

    // Polje za unos rednog broja
    private lateinit var inputField: EditText

    // Dugmad za bojenje i resetovanje
    private lateinit var colorButton: Button
    private lateinit var resetButton: Button

    // Pratimo koji je igrač na potezu
    private var currentPlayer = true // true = crveni, false = plavi

    // Čuvamo boju svakog polja radi očuvanja stanja
    private val fieldStates = Array(9) { Color.WHITE }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fields = listOf(
            findViewById(R.id.p1), findViewById(R.id.p2), findViewById(R.id.p3),
            findViewById(R.id.p4), findViewById(R.id.p5), findViewById(R.id.p6),
            findViewById(R.id.p7), findViewById(R.id.p8), findViewById(R.id.p9)
        )

        inputField = findViewById(R.id.inputNumber)
        colorButton = findViewById(R.id.colorButton)
        resetButton = findViewById(R.id.resetButton)

        colorButton.setOnClickListener {
            val input = inputField.text.toString().toIntOrNull()
            if (input != null && input in 1..9) {
                val index = input - 1
                val color = if (currentPlayer) Color.RED else Color.BLUE
                fields[index].setBackgroundColor(color)
                fieldStates[index] = color
                currentPlayer = !currentPlayer
            }
        }

        resetButton.setOnClickListener {
            for (i in 0..8) {
                fields[i].setBackgroundColor(Color.WHITE)
                fieldStates[i] = Color.WHITE
            }
            currentPlayer = true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putIntArray("fieldStates", fieldStates.toIntArray())
        outState.putBoolean("currentPlayer", currentPlayer)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val savedColors = savedInstanceState.getIntArray("fieldStates")
        if (savedColors != null) {
            for (i in savedColors.indices) {
                fields[i].setBackgroundColor(savedColors[i])
                fieldStates[i] = savedColors[i]
            }
        }
        currentPlayer = savedInstanceState.getBoolean("currentPlayer", true)
    }
}
