package hu.bme.aut.mwnhf

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import hu.bme.aut.mwnhf.data.Trip
import hu.bme.aut.mwnhf.data.TripDb
import hu.bme.aut.mwnhf.databinding.ActivityDetailsBinding
import kotlin.concurrent.thread

class DetailsActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var database: TripDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = TripDb.getDatabase(applicationContext)

        thread{
            val trip = database.TripDao().getOne(intent.getLongExtra("tripId",0))
            runOnUiThread {
                binding.lengthMeter.text = trip.getLengthInM()
                binding.averageSpeed.text = trip.getAverageSpeed()
                binding.downhill.text = trip.getDownhill()
                binding.uphill.text = trip.getUphill()
                binding.startTime.text = trip.getStart()
                binding.endTime.text = trip.getEnd()
                binding.lengthTime.text = trip.getLengthTime()
                binding.maxSpeed.text = trip.getMaxSpeed()
            }
        }
    }
}
