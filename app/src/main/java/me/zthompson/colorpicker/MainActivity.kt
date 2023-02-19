package me.zthompson.colorpicker

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*

const val LOG_TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var colorDivider: View

    private lateinit var redSwitch: Switch
    private lateinit var redSeekBar: SeekBar
    private lateinit var redTextView: TextView

    private lateinit var greenSwitch: Switch
    private lateinit var greenSeekBar: SeekBar
    private lateinit var greenTextView: TextView

    private lateinit var blueSwitch: Switch
    private lateinit var blueSeekBar: SeekBar
    private lateinit var blueTextView: TextView

    private lateinit var resetButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // RED SECTION
        redTextView = findViewById(R.id.redTextView)
        redSwitch = findViewById(R.id.redSwitch)
        redSwitch.setOnClickListener {
            updateColorDisplay()
        }
        redSeekBar = findViewById(R.id.redSeekBar)
        redSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged (seekBar: SeekBar, progress: Int, fromUser: Boolean){
                val number = progress.toFloat() / 255.0
                redTextView.text = String.format("%.2f", number)
                updateColorDisplay()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        // GREEN SECTION
        greenTextView = findViewById(R.id.greenTextView)
        greenSwitch = findViewById(R.id.greenSwitch)
        greenSwitch.setOnClickListener {
            updateColorDisplay()
        }
        greenSeekBar = findViewById(R.id.greenSeekBar)
        greenSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged (seekBar: SeekBar, progress: Int, fromUser: Boolean){
                val number = progress.toFloat() / 255.0
                greenTextView.text = String.format("%.2f", number)
                updateColorDisplay()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        // BLUE SECTION
        blueTextView = findViewById(R.id.blueTextView)
        blueSwitch = findViewById(R.id.blueSwitch)
        blueSwitch.setOnClickListener {
            updateColorDisplay()
        }
        blueSeekBar = findViewById(R.id.blueSeekBar)
        blueSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged (seekBar: SeekBar, progress: Int, fromUser: Boolean){
                val number = progress.toFloat() / 255.0
                blueTextView.text = String.format("%.2f", number)
                updateColorDisplay()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })


        colorDivider = findViewById(R.id.colorDivider)
        colorDivider.setBackgroundColor(Color.BLACK)
        colorDivider.setOnClickListener{
            //Toast.makeText(this, "The Color is: ", Toast.LENGTH_SHORT).show()
        }

        resetButton = findViewById(R.id.resetButton)
        resetButton.setOnClickListener{
            redSwitch.isChecked = true
            greenSwitch.isChecked = true
            blueSwitch.isChecked = true

            redSeekBar.progress = 0
            greenSeekBar.progress = 0
            blueSeekBar.progress = 0
            updateColorDisplay()
        }
        updateColorDisplay()
    }

    fun updateColorDisplay() {
        val r = if (redSwitch.isChecked) redSeekBar.progress else 0
        val g = if (greenSwitch.isChecked) greenSeekBar.progress else 0
        val b = if (blueSwitch.isChecked) blueSeekBar.progress else 0
        colorDivider.setBackgroundColor(Color.rgb(r, g, b))
    }
}