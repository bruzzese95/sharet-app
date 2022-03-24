package it.sapienza.macc.sharet.sharedresourcecalendar

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.sapienza.macc.sharet.databinding.CalendarEventItemViewBinding

class SharedResourceCalendarEventsAdapter(val onClick: (Event) -> Unit) :
    RecyclerView.Adapter<SharedResourceCalendarEventsAdapter.SharedResourceCalendarEventsViewHolder>() {

    val events = mutableListOf<Event>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SharedResourceCalendarEventsViewHolder {
        return SharedResourceCalendarEventsViewHolder(
            CalendarEventItemViewBinding.inflate(parent.context.layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(viewHolder: SharedResourceCalendarEventsViewHolder, position: Int) {
        viewHolder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size

    inner class SharedResourceCalendarEventsViewHolder(private val binding: CalendarEventItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onClick(events[bindingAdapterPosition])
            }
        }

        fun bind(event: Event) {
            binding.itemEventText.text = event.text
        }
    }
}