package hu.bme.aut.mwnhf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.os.SystemClock.elapsedRealtime
import android.widget.Chronometer
import android.widget.TextView
import hu.bme.aut.mwnhf.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isRunning: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.start.setOnClickListener {
            binding.time.base = SystemClock.elapsedRealtime()
            binding.time.start()
        }
        binding.stop.setOnClickListener {
            binding.time.stop()
        }
    }
}
