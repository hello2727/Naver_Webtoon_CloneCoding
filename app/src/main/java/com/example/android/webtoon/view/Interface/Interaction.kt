package com.example.android.webtoon.view.adapter.Interface

import android.view.View
import com.example.android.webtoon.model.remote.RecommendedItem

interface Interaction : View.OnClickListener {
    fun onRecommendedItemClicked(RecommendedItem: RecommendedItem)
}