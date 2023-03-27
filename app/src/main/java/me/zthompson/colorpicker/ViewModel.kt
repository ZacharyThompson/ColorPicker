package me.zthompson.colorpicker

import android.graphics.Color
import android.util.Log
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ViewModel : ViewModel() {
    private var color = Color.rgb(0, 0, 0)

    private var redEnabled = true
    private var greenEnabled = true
    private var blueEnabled = true

    private val prefs = PreferencesRepository.getRepository()
    fun saveColor() {
        viewModelScope.launch {
            prefs.saveColor(color)
            prefs.saveToggles(redEnabled, blueEnabled, greenEnabled)
        }
    }

    fun loadColor() {
        Log.i(LOG_TAG, "loadColor called")
        GlobalScope.launch {
            prefs.color.collectLatest {
                Log.i(LOG_TAG, "color loaded: $it")
                color = it
            }
        }
        GlobalScope.launch {
            prefs.redEnabled.collectLatest {
                Log.i(LOG_TAG, "redEnabled loaded: $it")
                redEnabled = it
            }
        }
        GlobalScope.launch {
            prefs.greenEnabled.collectLatest {
                Log.i(LOG_TAG, "greenEnabled loaded: $it")
                greenEnabled = it
            }
        }
        GlobalScope.launch {
            prefs.blueEnabled.collectLatest {
                Log.i(LOG_TAG, "blueEnabled loaded: $it")
                blueEnabled = it
            }
        }
        Thread.sleep(1000)
    }


    fun getDisplayColor(): Int {
        val r = if (redEnabled) Color.red(color) else 0
        val g = if (greenEnabled) Color.green(color) else 0
        val b = if (blueEnabled) Color.blue(color) else 0
        return Color.rgb(r, g, b)
    }

    fun getColor(): Int {
        return color
    }

    fun setRedComponent(n: Int) {
        val r = n
        val g = Color.green(color)
        val b = Color.blue(color)
        color = Color.rgb(r, g, b)
        saveColor()
    }

    fun setGreenComponent(n: Int) {
        val r = Color.red(color)
        val g = n
        val b = Color.blue(color)
        color = Color.rgb(r, g, b)
        saveColor()
    }

    fun setBlueComponent(n: Int) {
        val r = Color.red(color)
        val g = Color.green(color)
        val b = n
        color = Color.rgb(r, g, b)
        saveColor()
    }

    fun resetColor() {
        redEnabled = true
        greenEnabled = true
        blueEnabled = true

        color = Color.rgb(0, 0, 0)
        saveColor()
    }

    fun setRedEnabled(b: Boolean) {
        redEnabled = b
    }
    fun isRedEnabled(): Boolean {
        return redEnabled
    }

    fun setGreenEnabled(b: Boolean) {
        greenEnabled = b
    }
    fun isGreenEnabled(): Boolean {
        return greenEnabled
    }

    fun setBlueEnabled(b: Boolean) {
        blueEnabled = b
    }
    fun isBlueEnabled(): Boolean {
        return blueEnabled
    }
}