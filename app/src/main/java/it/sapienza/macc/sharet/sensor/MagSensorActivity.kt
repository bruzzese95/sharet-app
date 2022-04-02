package it.sapienza.macc.sharet.sensor

import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import it.sapienza.macc.sharet.R

class MagSensorActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var magneticX: TextView
    private lateinit var magneticY: TextView
    private lateinit var magneticZ: TextView
    private lateinit var sensorManager: SensorManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mag_sensor)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        //Capture magnetic sensor related view elements
        magneticX = findViewById(R.id.valMag_X) as TextView
        magneticY = findViewById(R.id.valMag_Y) as TextView
        magneticZ = findViewById(R.id.valMag_Z) as TextView

        //Register magnetic sensor
        sensorManager.registerListener(this,
            sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
            SensorManager.SENSOR_DELAY_NORMAL)
    }


    @Override
    override fun onPause() {
        //Unregister the listener
        sensorManager.unregisterListener(this)
        super.onPause()
    }

    @Override
    override fun onStop() {
        //Unregister the listener
        sensorManager.unregisterListener(this)
        super.onStop()
    }

    @Override
    override fun onResume() {
        super.onResume()

        //Register magnetic sensor
        sensorManager.registerListener(this,
            sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
            SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        synchronized(this) {
            if (sensorEvent != null) {
                if (sensorEvent.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
                    magneticX.text = sensorEvent.values[0].toString()
                    magneticY.text = sensorEvent.values[1].toString()
                    magneticZ.text = sensorEvent.values[2].toString()

                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //Can be ignored
    }

}