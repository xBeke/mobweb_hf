package hu.bme.aut.mwnhf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.os.SystemClock.elapsedRealtime
import android.util.Log
import android.widget.Chronometer
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import hu.bme.aut.mwnhf.adapter.TripAdapter
import hu.bme.aut.mwnhf.data.Trip
import hu.bme.aut.mwnhf.data.TripDb
import hu.bme.aut.mwnhf.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: TripDb
    private var isRunning: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        database = TripDb.getDatabase(applicationContext)

        binding.start.setOnClickListener {
            binding.time.base = elapsedRealtime()
            binding.time.start()
        }
        binding.stop.setOnClickListener {
            binding.time.stop()
            AlertDialog.Builder(this)
                .setMessage(getString(R.string.qna))
                .setPositiveButton(getString(R.string.yes)) { _, _ -> save() }
                .setNegativeButton(getString(R.string.nop), null)
                .show()
            binding.time.text = getString(R.string.zeroz)
        }
        binding.history.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }

    private fun save(){
        var trip = {
            val startTime = 2
            val endTime = 4
        }

        add(Trip(startTime = 2, endTime = 4))
        showDetails()
    }

    private fun showDetails(){

    }

    private fun add(item: Trip) {
        thread {
            database.TripDao().insert(item)
            Log.d("HistoryActivity", "Trip update was successful")
        }
    }
}
