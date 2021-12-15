package hu.bme.aut.mwnhf.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.mwnhf.HistoryActivity
import hu.bme.aut.mwnhf.R
import hu.bme.aut.mwnhf.data.Trip
import hu.bme.aut.mwnhf.databinding.TripListBinding
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent

class TripAdapter(private val listener: TripClickListener) :
    RecyclerView.Adapter<TripAdapter.TripViewHolder>() {

    private val items = mutableListOf<Trip>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TripViewHolder(
        TripListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val tripItem = items[position]
        val elapsedTimeFormat = SimpleDateFormat("HH:mm:ss")
        elapsedTimeFormat.timeZone = (TimeZone.getTimeZone("GMT"))
        val startEndFormat = SimpleDateFormat("HH:mm")
        startEndFormat.timeZone = (TimeZone.getTimeZone("GMT+1"))

        holder.binding.elapsedTime.text = elapsedTimeFormat.format(Date(tripItem.endTime - tripItem.startTime))
        holder.binding.height.text = "12km"
        holder.binding.maxSpeed.text = "40km/h"
        holder.binding.start.text = startEndFormat.format(Date(tripItem.startTime))
        holder.binding.end.text = startEndFormat.format(Date(tripItem.endTime))

        holder.binding.remove.setOnClickListener {
            AlertDialog.Builder(listener as AppCompatActivity)
                .setMessage("Do you wanna forget this?")
                .setPositiveButton("Yes :(") { _, _ -> listener.onItemDeleted(tripItem) }
                .setNegativeButton("NO", null)
                .show()
        }

        holder.binding.card.setOnClickListener {
            listener.openDetails(tripItem)
        }
    }

    override fun getItemCount(): Int = items.size

    interface TripClickListener {
        fun onItemDeleted(item: Trip)
        fun openDetails(item: Trip)
    }

    inner class TripViewHolder(val binding: TripListBinding) : RecyclerView.ViewHolder(binding.root)

    fun addItem(item: Trip) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun update(trips: List<Trip>) {
        items.clear()
        items.addAll(trips)
        notifyDataSetChanged()
    }
}
