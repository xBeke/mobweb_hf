package hu.bme.aut.mwnhf


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock.elapsedRealtime
import android.util.Log
import androidx.appcompat.app.AlertDialog
import hu.bme.aut.mwnhf.data.Trip
import hu.bme.aut.mwnhf.data.TripDb
import hu.bme.aut.mwnhf.databinding.ActivityMainBinding
import java.util.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: TripDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        database = TripDb.getDatabase(applicationContext)
        binding.stop.isEnabled = false

        var actualTrip = Trip(startTime = 0, endTime = 0)

        binding.start.setOnClickListener {
            binding.time.base = elapsedRealtime()
            binding.time.start()
            binding.start.isEnabled = false
            binding.stop.isEnabled = true
            actualTrip.startTime = Date().time
        }

        binding.stop.setOnClickListener {
            binding.time.stop()
            actualTrip.endTime = Date().time
            AlertDialog.Builder(this)
                .setMessage(getString(R.string.qna))
                .setPositiveButton(getString(R.string.yes)) { _, _ -> save(actualTrip) }
                .setNegativeButton(getString(R.string.nop), null)
                .show()
            binding.time.text = getString(R.string.zeroz)
            binding.start.isEnabled = true
            binding.stop.isEnabled = false
        }

        binding.history.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }

    private fun save(actualTrip: Trip){
        add(actualTrip)
        showDetails()
    }

    private fun showDetails(){
        thread {
            startActivity(
                Intent(this, DetailsActivity::class.java).putExtra(
                    "tripId",
                    database.TripDao().getLastId()
                )
            )
        }
    }

    private fun add(item: Trip) {
        thread {
            database.TripDao().insert(item)
            Log.d("HistoryActivity", "Trip update was successful")
        }
    }
}
