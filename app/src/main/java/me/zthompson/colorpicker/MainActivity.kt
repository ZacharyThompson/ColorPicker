package me.zthompson.colorpicker

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.lifecycle.ViewModelProvider

const val LOG_TAG = "MainActivity"
const val COLOR_KEY = "color"
const val RED_ENABLED_KEY = "red_enabled"
const val GREEN_ENABLED_KEY = "green_enabled"
const val BLUE_ENABLED_KEY = "blue_enabled"
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


        viewModel.loadColor()

        // RED SECTION
        redTextView = findViewById(R.id.redTextView)
        redTextView.text = String.format("%.2f", viewModel.getColor().red.toFloat() / 255.0)
        redTextView.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    val text = redTextView.text.toString()
                    val value = (text.toFloat() * 255.0).toInt()
                    if (value > 255 || value < 0) {
                        Toast.makeText(this, "Enter a value from 0 to 1", Toast.LENGTH_SHORT).show()
                    } else {
                        redSeekBar.progress = value
                        updateView()
                    }
                    true
                }
                else -> {
                    false
                }
            }
        }

        redSwitch = findViewById(R.id.redSwitch)
        redSwitch.isChecked = viewModel.isRedEnabled()
        redSwitch.setOnClickListener {
            updateView()
        }

        redSeekBar = findViewById(R.id.redSeekBar)
        redSeekBar.progress = viewModel.getColor().red
        redSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged (seekBar: SeekBar, progress: Int, fromUser: Boolean){
                redTextView.text = String.format("%.2f", progress.toFloat() / 255.0)
                updateView()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        // GREEN SECTION
        greenTextView = findViewById(R.id.greenTextView)
        greenTextView.text = String.format("%.2f", viewModel.getColor().green.toFloat() / 255.0)
        greenTextView.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    val text = greenTextView.text.toString()
                    val value = (text.toFloat() * 255.0).toInt()
                    if (value > 255 || value < 0) {
                        Toast.makeText(this, "Enter a value from 0 to 1", Toast.LENGTH_SHORT).show()
                    } else {
                        greenSeekBar.progress = value
                        updateView()
                    }
                    true
                }
                else -> {
                    false
                }
            }
        }

        greenSwitch = findViewById(R.id.greenSwitch)
        greenSwitch.isChecked = viewModel.isGreenEnabled()
        greenSwitch.setOnClickListener {
            updateView()
        }

        greenSeekBar = findViewById(R.id.greenSeekBar)
        greenSeekBar.progress = viewModel.getColor().green
        greenSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged (seekBar: SeekBar, progress: Int, fromUser: Boolean){
                greenTextView.text = String.format("%.2f", progress.toFloat() / 255.0)
                updateView()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        // BLUE SECTION
        blueTextView = findViewById(R.id.blueTextView)
        blueTextView.text = String.format("%.2f", viewModel.getColor().blue.toFloat() / 255.0)
        blueTextView.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    val text = blueTextView.text.toString()
                    val value = (text.toFloat() * 255.0).toInt()
                    if (value > 255 || value < 0) {
                        Toast.makeText(this, "Enter a value from 0 to 1", Toast.LENGTH_SHORT).show()
                    } else {
                        blueSeekBar.progress = value
                        updateView()
                    }
                    true
                }
                else -> {
                    false
                }
            }
        }

        blueSwitch = findViewById(R.id.blueSwitch)
        blueSwitch.isChecked = viewModel.isBlueEnabled()
        blueSwitch.setOnClickListener {
            updateView()
        }

        blueSeekBar = findViewById(R.id.blueSeekBar)
        blueSeekBar.progress = viewModel.getColor().blue
        blueSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged (seekBar: SeekBar, progress: Int, fromUser: Boolean){
                blueTextView.text = String.format("%.2f", progress.toFloat() / 255.0)
                updateView()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })


        colorDivider = findViewById(R.id.colorDivider)
        colorDivider.setBackgroundColor(viewModel.getDisplayColor())

        resetButton = findViewById(R.id.resetButton)
        resetButton.setOnClickListener{
            redSwitch.isChecked = true
            greenSwitch.isChecked = true
            blueSwitch.isChecked = true

            redSeekBar.progress = 0
            greenSeekBar.progress = 0
            blueSeekBar.progress = 0

            viewModel.resetColor()
            updateView()
        }
        updateView()
    }

    fun updateColorDisplay() {
        colorDivider.setBackgroundColor(viewModel.getDisplayColor())
    }

    fun updateView() {
        viewModel.setRedEnabled(redSwitch.isChecked)
        viewModel.setGreenEnabled(greenSwitch.isChecked)
        viewModel.setBlueEnabled(blueSwitch.isChecked)

        viewModel.setRedComponent(redSeekBar.progress)
        viewModel.setGreenComponent(greenSeekBar.progress)
        viewModel.setBlueComponent(blueSeekBar.progress)

        updateColorDisplay()
    }

    private val viewModel: ViewModel by lazy {
        PreferencesRepository.initialize(this)
        ViewModelProvider(this)[ViewModel::class.java]
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(LOG_TAG, "The color state is saved")
        outState.putInt(COLOR_KEY, viewModel.getColor())
        outState.putBoolean(RED_ENABLED_KEY, viewModel.isRedEnabled())
        outState.putBoolean(GREEN_ENABLED_KEY, viewModel.isBlueEnabled())
        outState.putBoolean(BLUE_ENABLED_KEY, viewModel.isGreenEnabled())
    }
}