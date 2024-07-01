package com.example.task1.ui.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.task1.R
import com.example.task1.data.models.Banner
import com.squareup.picasso.Picasso

class BannerAdapter(private val bannerList: List<Banner>) :
    RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_banner, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(bannerList[position])
    }

    override fun getItemCount(): Int {
        return bannerList.size
    }

    inner class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageviewBanners: ImageView = itemView.findViewById(R.id.banner_image_view)

        fun bind(banner: Banner) {

            Picasso.get()
                .load(banner.imageUrl)
                .placeholder(R.drawable.rounded_10k_24) // Placeholder image while loading
                .error(androidx.core.R.drawable.ic_call_decline) // Image to show if loading fails
                .into(imageviewBanners)

        }
    }
}
