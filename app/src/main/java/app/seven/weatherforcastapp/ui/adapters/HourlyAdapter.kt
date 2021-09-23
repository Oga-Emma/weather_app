package app.seven.weatherforcastapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.seven.weatherforcastapp.databinding.RvHourlyItemBinding
import app.seven.weatherforcastapp.model.HourlyWeatherData
import app.seven.weatherforcastapp.utils.getWeatherIcon
import androidx.recyclerview.widget.AsyncListDiffer
import app.seven.weatherforcastapp.R
import java.text.SimpleDateFormat
import java.time.LocalDateTime


class HourlyAdapter(): RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder>() {
    private var list: List<HourlyWeatherData> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val binding = RvHourlyItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return HourlyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        val item = list[position]
        holder.apply {
            val simpleDateFormat = SimpleDateFormat("hh:mm a")
            val dateString: String = simpleDateFormat.format(item.dt * 1000)
            binding.hourLabel.text = dateString
            binding.tempLabel.text = "${item.temp}"+ holder.binding.root.context.getString(R.string.degreee)
            binding.weatherIV.setImageResource(getWeatherIcon(item.weather[0].main))
        }
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    private val DIFF_CALLBACK: DiffUtil.ItemCallback<HourlyWeatherData> =
        object : DiffUtil.ItemCallback<HourlyWeatherData>() {
            override fun areItemsTheSame(
                oldItem: HourlyWeatherData,
                newItem: HourlyWeatherData
            ): Boolean {
               return  oldItem.dt == newItem.dt
            }

            override fun areContentsTheSame(
                oldItem: HourlyWeatherData,
                newItem: HourlyWeatherData
            ): Boolean {
               return oldItem == newItem
            }
        }

    private val differ: AsyncListDiffer<HourlyWeatherData> =
        AsyncListDiffer(this, DIFF_CALLBACK)

    fun updateList(items: List<HourlyWeatherData>) {
//        differ.submitList(items)
        list = items
        notifyDataSetChanged()
    }

    inner class HourlyViewHolder(val binding: RvHourlyItemBinding): RecyclerView.ViewHolder(binding.root)
}