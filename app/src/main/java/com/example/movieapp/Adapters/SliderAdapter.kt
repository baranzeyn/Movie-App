package com.example.movieapp.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.Domain.SliderItems
import com.example.movieapp.databinding.SlideItemContainerBinding

class SliderAdapter(
    private var sliderItems: MutableList<SliderItems> = mutableListOf(),
) : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    inner class SliderViewHolder(val binding: SlideItemContainerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setImage(imageResId: Int) {
            val multi = MultiTransformation(CenterCrop(), RoundedCorners(dpToPx(16, binding.ivSlide.context)))
            Glide.with(binding.ivSlide.context)
                .load(imageResId)
                .apply(RequestOptions.bitmapTransform(multi)) // 16dp yuvarlak köşeler
                .into(binding.ivSlide)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SlideItemContainerBinding.inflate(inflater, parent, false)
        return SliderViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        val sliderItem = sliderItems[position]
        holder.setImage(sliderItem.ivPath)
    }

    fun setImages(newItems: List<SliderItems>) {
        sliderItems.clear()
        sliderItems.addAll(newItems)
        notifyItemRangeInserted(0, newItems.size)
    }

    private fun dpToPx(dp: Int, context: Context): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }
}
