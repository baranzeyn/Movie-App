package com.example.movieapp.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.movieapp.Domain.SliderItems
import com.example.movieapp.databinding.SlideItemContainerBinding

class SliderAdapter(
    private val sliderItems: MutableList<SliderItems> = mutableListOf(),
    private val viewPager2: ViewPager2
) : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    inner class SliderViewHolder(val binding: SlideItemContainerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val binding = SlideItemContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SliderViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        val sliderItem = sliderItems[position]
        // ViewBinding kullanarak bileşenleri güncelleyin
        holder.binding.ivSlide.setImageResource(sliderItem.ivPath)
    }
}
