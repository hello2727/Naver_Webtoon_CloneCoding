package com.example.android.webtoon.webtoon.Interface

import android.view.View
import com.example.android.webtoon.webtoon.data.RecommendedItem

interface Interaction : View.OnClickListener {
    fun onRecommendedItemClicked(RecommendedItem: RecommendedItem)
}