package com.example.android.webtoon.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.webtoon.R
import com.example.android.webtoon.util.GlideApp
import com.example.android.webtoon.view.adapter.Interface.Interaction
import com.example.android.webtoon.model.RecommendedItem
import kotlinx.android.synthetic.main.item_layout_recommended.view.*

class WebtoonAdvertisementViewPagerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ITEM_COUNT = 10
    }

    private var recommendedItemList: List<RecommendedItem>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecommendedViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout_recommended, parent, false)
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
    class RecommendedViewHolder
    constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(recommendedItem: RecommendedItem) {
            itemView.iv_recommended_item.setImageResource(recommendedItem.image)
            /*
            추천웹툰 이미지 로딩
             */
//            itemView.iv_recommended_item.setImageResource(recommendedItem.image)
//            GlideApp.with(itemView).load(recommendedItem.image).placeholder(R.drawable.sample4).into(itemView.iv_recommended_item)
        }
    }
}