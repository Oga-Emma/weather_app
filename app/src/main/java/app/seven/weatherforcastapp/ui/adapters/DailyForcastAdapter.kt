package app.seven.weatherforcastapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.seven.weatherforcastapp.R
import app.seven.weatherforcastapp.databinding.RvDailyItemBinding
import app.seven.weatherforcastapp.databinding.RvHourlyItemBinding
import app.seven.weatherforcastapp.model.DailyWeatherData
import app.seven.weatherforcastapp.model.HourlyWeatherData
import app.seven.weatherforcastapp.utils.getWeatherIcon
import java.text.SimpleDateFormat

class DailyForcastAdapter(): RecyclerView.Adapter<DailyForcastAdapter.DailyForcastViewHolder>() {
    private var list: List<DailyWeatherData> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForcastViewHolder {
        val binding = RvDailyItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return DailyForcastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyForcastViewHolder, position: Int) {
        val item = list[position]
        holder.apply {
            val simpleDateFormat = SimpleDateFormat("MMM, dd")
            val dateString: String = simpleDateFormat.format(item.dt * 1000)
            binding.dateLabel.text = dateString
            binding.tempLabel.text = "${item.temp.let { 
                ((it.max + it.min)/2).toInt()
            }}"+ holder.binding.root.context.getString(R.string.degreee)
            binding.weatherIV.setImageResource(getWeatherIcon(item.weather[0].main))
        }
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    private val DIFF_CALLBACK: DiffUtil.ItemCallback<DailyWeatherData> =
        object : DiffUtil.ItemCallback<DailyWeatherData>() {
            override fun areItemsTheSame(
                oldItem: DailyWeatherData,
                newItem: DailyWeatherData
            ): Boolean {
                return  oldItem.dt == newItem.dt
            }

            override fun areContentsTheSame(
                oldItem: DailyWeatherData,
                newItem: DailyWeatherData
            ): Boolean {
                return oldItem == newItem
            }
        }

    private val differ: AsyncListDiffer<DailyWeatherData> =
        AsyncListDiffer(this, DIFF_CALLBACK)

    fun updateList(items: List<DailyWeatherData>) {
//        differ.submitList(items)
        list = items
        notifyDataSetChanged()
    }

    inner class DailyForcastViewHolder(val binding: RvDailyItemBinding): RecyclerView.ViewHolder(binding.root)
}