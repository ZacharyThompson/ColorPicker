package me.zthompson.colorpicker

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.ViewModelProvider

const val LOG_TAG = "MainActivity"
const val RED_KEY = "red"
const val GREEN_KEY = "green"
const val BLUE_KEY = "blue"
class MainActivity : AppCompatActivity() {
    private lateinit var colorDivider: View

    private lateinit var redSwitch: Switch
    private lateinit var redSeekBar: SeekBar
    private lateinit var redEditText: TextView

    private lateinit var greenSwitch: Switch
    private lateinit var greenSeekBar: SeekBar
    private lateinit var greenTextView: TextView

    private lateinit var blueSwitch: Switch
    private lateinit var blueSeekBar: SeekBar
    private lateinit var blueTextView: TextView

    private lateinit var resetButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.loadColor()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // RED SECTION
        redEditText = findViewById(R.id.redEditText)
        redSwitch = findViewById(R.id.redSwitch)
        redSwitch.setOnClickListener {
            updateColorDisplay()
        }
        redSeekBar = findViewById(R.id.redSeekBar)
        redSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged (seekBar: SeekBar, progress: Int, fromUser: Boolean){
                viewModel.setRedComponent(progress)
                redEditText.text = String.format("%.2f", progress.toFloat() / 250.0)
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
                viewModel.setGreenComponent(progress)
                greenTextView.text = String.format("%.2f", progress.toFloat() / 250.0)
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
                viewModel.setBlueComponent(progress)
                blueTextView.text = String.format("%.2f", progress.toFloat() / 250.0)
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

            viewModel.resetColor()
            updateColorDisplay()
        }
        updateColorDisplay()
        redSeekBar.progress = viewModel.getRedComponent()
        greenSeekBar.progress = viewModel.getGreenComponent()
        blueSeekBar.progress = viewModel.getBlueComponent()
    }

    fun updateColorDisplay() {
        // TODO: make it work with switches
        Log.i(LOG_TAG, "Color value: ${viewModel.getColorValue()}")
        colorDivider.setBackgroundColor(viewModel.getColorValue(redSwitch.isChecked, greenSwitch.isChecked, blueSwitch.isChecked))
        Log.i(LOG_TAG, "Color finished: ${viewModel.getColorValue()}")
    }

    private val viewModel: ViewModel by lazy {
        PreferencesRepository.initialize(this)
        ViewModelProvider(this)[ViewModel::class.java]
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(LOG_TAG, "The counter value is saved")
        outState.putInt(RED_KEY, viewModel.getRedComponent())
        outState.putInt(GREEN_KEY, viewModel.getGreenComponent())
        outState.putInt(BLUE_KEY, viewModel.getBlueComponent())
    }
}