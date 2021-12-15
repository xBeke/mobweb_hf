package hu.bme.aut.mwnhf.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.mwnhf.data.Trip
import hu.bme.aut.mwnhf.databinding.TripListBinding

class TripAdapter(private val listener: TripClickListener) :
    RecyclerView.Adapter<TripAdapter.TripViewHolder>() {

    private val items = mutableListOf<Trip>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TripViewHolder(
        TripListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val tripItem = items[position]

        holder.binding.elapsedTime.text = tripItem.startTime.toString()
        holder.binding.height.text = "12km"
        holder.binding.maxSpeed.text = "40km/h"
    }

    override fun getItemCount(): Int = items.size

    interface TripClickListener {
        fun onItemChanged(item: Trip)
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
