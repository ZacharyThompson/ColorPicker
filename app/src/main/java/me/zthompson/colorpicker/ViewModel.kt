package me.zthompson.colorpicker

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ViewModel : ViewModel() {
    private var redComponent: Int = 0
    private var greenComponent: Int = 0
    private var blueComponent: Int = 0

    private val prefs = PreferencesRepository.getRepository()
    fun saveColor() {
        viewModelScope.launch {
            prefs.saveColor(redComponent, greenComponent, blueComponent)
        }
    }

    fun loadColor() {
        GlobalScope.launch {
            prefs.red.collectLatest {
                redComponent = it
            }
            prefs.green.collectLatest {
                greenComponent = it
            }
            prefs.blue.collectLatest {
                blueComponent = it
            }
        }
        Thread.sleep(1000)
    }



    fun getColorValue(r: Boolean =true, g: Boolean =true, b: Boolean =true): Int {
        return Color.rgb(
            if (r) redComponent else 0,
            if (g) greenComponent else 0,
            if (b) blueComponent else 0
        )
    }

    fun getRedComponent(): Int {
        return redComponent
    }
    fun setRedComponent(n: Int) {
        redComponent = n
        Log.d(LOG_TAG, "Red set to: $n")
        saveColor()
    }

    fun getGreenComponent(): Int {
        return greenComponent
    }
    fun setGreenComponent(n: Int) {
        greenComponent = n
        Log.d(LOG_TAG, "Green set to: $n")
        saveColor()
    }

    fun getBlueComponent(): Int {
        return blueComponent
    }
    fun setBlueComponent(n: Int) {
        blueComponent = n
        Log.d(LOG_TAG, "Blue set to: $n")
        saveColor()
    }

    fun resetColor() {
        redComponent = 0
        greenComponent = 0
        blueComponent = 0
        saveColor()
    }
}