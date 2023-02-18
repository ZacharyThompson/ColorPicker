package me.zthompson.colorpicker

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView

const val LOG_TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var colorDivider: View

    private lateinit var redSwitch: Switch
    private lateinit var redSeekBar: SeekBar
    private lateinit var redTextView: TextView


    private var color: Int = Color.BLACK
    private var redComponent: Int = 0
    private var redOn: Boolean = true
    private var greenComponent: Int = 0
    private var blueComponent: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // RED SECTION
        redTextView = findViewById(R.id.redTextView)
        redSwitch = findViewById(R.id.redSwitch)
        redSwitch.setOnClickListener {
            val switch: Switch = (it as Switch)
            redOn = switch.isChecked
            if (redOn) {
                color = Color.rgb(redComponent, blueComponent, greenComponent)
                colorDivider.setBackgroundColor(color)
            } else {
                color = Color.rgb(0, blueComponent, greenComponent)
                colorDivider.setBackgroundColor(color)
            }
        }
        redSeekBar = findViewById(R.id.redSeekBar)
        redSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged (seekBar: SeekBar, progress: Int, fromUser: Boolean){
                val number = progress.toFloat() / 255.0
                redTextView.text = String.format("%.2f", number)
                redComponent = progress
                if (redOn) {
                    color = Color.rgb(redComponent, blueComponent, greenComponent)
                    colorDivider.setBackgroundColor(color)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        // BLUE SECTION

        // GREEN SECTION

        colorDivider = findViewById(R.id.colorDivider)
        println(String.format("MY COLOR = %x", Color.BLACK))
        colorDivider.setBackgroundColor(color)
    }
}