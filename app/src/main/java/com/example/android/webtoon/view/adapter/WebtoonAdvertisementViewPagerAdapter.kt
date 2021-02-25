package com.example.android.webtoon.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.webtoon.R
import com.example.android.webtoon.model.remote.RecommendedItem
import com.example.android.webtoon.view.adapter.Interface.Interaction
import kotlinx.android.synthetic.main.item_layout_recommended.view.*

class WebtoonAdvertisementViewPagerAdapter(private val interaction: Interaction) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ITEM_COUNT = 10
    }

    private var recommendedItemList: List<RecommendedItem>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecommendedViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout_recommended, parent, false),
            interaction
        )
    }

    override fun getItemCount(): Int {
        return ITEM_COUNT
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        recommendedItemList?.let { recommendedItemList ->
            (holder as RecommendedViewHolder).bind(recommendedItemList[position])
        }
    }

    fun submitList(list: List<RecommendedItem>?) {
        recommendedItemList = list
        notifyDataSetChanged()
    }

    //ViewHolder
    class RecommendedViewHolder(itemView: View, private val interaction: Interaction) : RecyclerView.ViewHolder(itemView) {
        fun bind(recommendedItem: RecommendedItem) {
            itemView.setOnClickListener{
                interaction.onRecommendedItemClicked(recommendedItem)
            }

            //웹툰광고배너 이미지 로드
            Glide.with(itemView.context).load(recommendedItem.IMAGE_ID).placeholder(R.drawable.thumbnail01).into(itemView.iv_recommended_item)
        }
    }
}
