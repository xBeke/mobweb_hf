package hu.bme.aut.mwnhf

import android.os.Bundle
import android.util.Log
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.mwnhf.adapter.TripAdapter
import hu.bme.aut.mwnhf.data.Trip
import hu.bme.aut.mwnhf.data.TripDb
import hu.bme.aut.mwnhf.databinding.ActivityHistoryBinding
import kotlin.concurrent.thread

class HistoryActivity : AppCompatActivity(), TripAdapter.TripClickListener {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var database: TripDb
    private lateinit var adapter: TripAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = TripDb.getDatabase(applicationContext)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = TripAdapter(this)
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.TripDao().getAll()
            runOnUiThread {
                adapter.update(items)
            }
        }
    }

    override fun onItemChanged(item: Trip) {
        thread {
            database.TripDao().update(item)
            Log.d("HistoryActivity", "Trip update was successful")
        }
    }
}
