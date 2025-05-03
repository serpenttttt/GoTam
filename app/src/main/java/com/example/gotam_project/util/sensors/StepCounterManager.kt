package com.example.gotam_project.util.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.sqrt

class StepCounterManager(context: Context) : SensorEventListener {

    private val sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val _stepCount = MutableStateFlow(0)
    val stepCount = _stepCount.asStateFlow()

    private var previousMagnitude: Float = 0.0F
    private var stepThreshold = 6.5
    private var stepCooldown = 250L // миллисекунды
    private var lastStepTime = 0L

    private val accelerometerSensor: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    var onStepCountUpdated: ((Int) -> Unit)? = null

    fun start() {
        accelerometerSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val magnitude = sqrt(x * x + y * y + z * z)
            val delta = magnitude - previousMagnitude
            previousMagnitude = magnitude

            val now = System.currentTimeMillis()

            if (delta > stepThreshold && (now - lastStepTime) > stepCooldown) {
                lastStepTime = now
                _stepCount.value += 1
                onStepCountUpdated?.invoke(_stepCount.value)  // Вызываем обновление шагов
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Не используется
    }
}