package com.example.android.webtoon.webtoon.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.with
import com.example.android.webtoon.R
import com.example.android.webtoon.webtoon.GlideApp
import com.example.android.webtoon.webtoon.Interface.Interaction
import com.example.android.webtoon.webtoon.data.RecommendedItem
import kotlinx.android.synthetic.main.item_layout_recommended.view.*
import okhttp3.internal.http2.Header

class ViewPagerAdapter(private val interaction: Interaction) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

    //일반 함수 1
    fun submitList(list: List<RecommendedItem>?) {
        recommendedItemList = list
        notifyDataSetChanged()
    }

    //ViewHolder
    class RecommendedViewHolder
    constructor(itemView: View, private val interaction: Interaction) : RecyclerView.ViewHolder(itemView) {
        fun bind(recommendedItem: RecommendedItem) {
            itemView.setOnClickListener {
                interaction.onRecommendedItemClicked(recommendedItem)
            }
            /*
            추천웹툰 이미지 로딩
             */
//            itemView.iv_recommended_item.setImageResource(recommendedItem.image)
            GlideApp.with(itemView).load(recommendedItem.image).placeholder(R.drawable.sample4).into(itemView.iv_recommended_item)
        }
    }
}