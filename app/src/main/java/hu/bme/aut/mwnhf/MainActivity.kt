package hu.bme.aut.mwnhf

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock.elapsedRealtime
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import hu.bme.aut.mwnhf.data.Coord


import hu.bme.aut.mwnhf.data.Trip
import hu.bme.aut.mwnhf.data.TripDb
import hu.bme.aut.mwnhf.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: TripDb
    private lateinit var locationManager: LocationManager
    private final var MIN_TIME: Long  = 1
    private final var MIN_DIST: Float = 0.0f
    lateinit var mLastLocation: Location
    private lateinit var thetext: TextView
    private lateinit var actualTrip: Trip
    init {
        actualTrip = Trip(startTime = 0, endTime = 0, cords = ArrayList())
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            thetext.text = ("Long: " + location.longitude +
                            "\nLat:" + location.latitude +
                            "\nSpeed:" + location.speed +
                            "\nTime:" + location.time +
                            "\nAltitude:" + location.altitude)
            actualTrip.cords.add(Coord(location.longitude, location.latitude, location.speed, location.time, location.altitude))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        thetext = binding.cords
        val view = binding.root

        setContentView(view)
        database = TripDb.getDatabase(applicationContext)
        binding.stop.isEnabled = false

        addStartOnClick()
        addStopOnClick()
        addHistoryOnClick()
    }

    private fun addStartOnClick() {
        binding.start.setOnClickListener {
            binding.time.base = elapsedRealtime()
            binding.time.start()
            binding.start.isEnabled = false
            binding.stop.isEnabled = true
            actualTrip.startTime = Date().time
            startTripTracker()
        }
    }

    private fun startTripTracker() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION), PackageManager.PERMISSION_GRANTED)

        } else {
            locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, locationListener)
        }
    }

    private fun addStopOnClick() {
        binding.stop.setOnClickListener {
            binding.time.stop()
            actualTrip.endTime = Date().time
            stopTripTracker()
            AlertDialog.Builder(this)
                .setMessage(getString(R.string.qna))
                .setPositiveButton(getString(R.string.yes)) { _, _ -> save(actualTrip) }
                .setNegativeButton(getString(R.string.nop), null)
                .show()
            binding.time.text = getString(R.string.zeroz)
            binding.start.isEnabled = true
            binding.stop.isEnabled = false
        }
    }

    private fun stopTripTracker(){
        locationManager.removeUpdates(locationListener)
    }

    private fun addHistoryOnClick() {
        binding.history.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }

    private fun save(actualTrip: Trip){
        if (actualTrip.cords.size > 1) {
            add(actualTrip)
        }
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
            showDetails()
        }
    }
}
