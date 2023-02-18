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
    private var redComponent: Int = 0
    private var redOn: Boolean = true

    private lateinit var greenSwitch: Switch
    private lateinit var greenSeekBar: SeekBar
    private lateinit var greenTextView: TextView
    private var greenComponent: Int = 0
    private var greenOn: Boolean = true

    private lateinit var blueSwitch: Switch
    private lateinit var blueSeekBar: SeekBar
    private lateinit var blueTextView: TextView
    private var blueComponent: Int = 0
    private var blueOn: Boolean = true

    private lateinit var resetButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // RED SECTION
        redTextView = findViewById(R.id.redTextView)
        redSwitch = findViewById(R.id.redSwitch)
        redSwitch.setOnClickListener {
            val switch: Switch = (it as Switch)
            redOn = switch.isChecked
            updateColorDisplay()
        }
        redSeekBar = findViewById(R.id.redSeekBar)
        redSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged (seekBar: SeekBar, progress: Int, fromUser: Boolean){
                val number = progress.toFloat() / 255.0
                redTextView.text = String.format("%.2f", number)
                redComponent = progress
                updateColorDisplay()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        // GREEN SECTION
        greenTextView = findViewById(R.id.greenTextView)
        greenSwitch = findViewById(R.id.greenSwitch)
        greenSwitch.setOnClickListener {
            val switch: Switch = (it as Switch)
            greenOn = switch.isChecked
            updateColorDisplay()
        }
        greenSeekBar = findViewById(R.id.greenSeekBar)
        greenSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged (seekBar: SeekBar, progress: Int, fromUser: Boolean){
                val number = progress.toFloat() / 255.0
                greenTextView.text = String.format("%.2f", number)
                greenComponent = progress
                updateColorDisplay()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        // BLUE SECTION
        blueTextView = findViewById(R.id.blueTextView)
        blueSwitch = findViewById(R.id.blueSwitch)
        blueSwitch.setOnClickListener {
            val switch: Switch = (it as Switch)
            blueOn = switch.isChecked
            updateColorDisplay()
        }
        blueSeekBar = findViewById(R.id.blueSeekBar)
        blueSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged (seekBar: SeekBar, progress: Int, fromUser: Boolean){
                val number = progress.toFloat() / 255.0
                blueTextView.text = String.format("%.2f", number)
                blueComponent = progress
                updateColorDisplay()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })


        colorDivider = findViewById(R.id.colorDivider)
        colorDivider.setBackgroundColor(Color.BLACK)
        colorDivider.setOnClickListener{
            Toast.makeText(this, "The Color is: ", Toast.LENGTH_SHORT).show()
        }

        resetButton = findViewById(R.id.resetButton)
        resetButton.setOnClickListener{
            redComponent = 0
            greenComponent = 0
            blueComponent = 0

            redSwitch.isChecked = true
            redOn = true
            greenSwitch.isChecked = true
            greenOn = true
            blueSwitch.isChecked = true
            blueOn = true

            redSeekBar.progress = 0
            greenSeekBar.progress = 0
            blueSeekBar.progress = 0
            updateColorDisplay()
        }
        updateColorDisplay()
    }

    fun updateColorDisplay() {
        val r = if (redOn) redComponent else 0
        val g = if (greenOn) greenComponent else 0
        val b = if (blueOn) blueComponent else 0
        colorDivider.setBackgroundColor(Color.rgb(r, g, b))
    }
}