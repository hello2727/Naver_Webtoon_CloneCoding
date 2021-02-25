package com.example.android.webtoon.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.webtoon.model.remote.RecommendedItem

class WebtoonAdvertisement: ViewModel() {
    private val _recommendedItemList: MutableLiveData<List<RecommendedItem>> = MutableLiveData()
    private val _currentPosition: MutableLiveData<Int> = MutableLiveData()

    val recommendedItemList: LiveData<List<RecommendedItem>>
        get() = _recommendedItemList
    val currentPosition: LiveData<Int>
        get() = _currentPosition

    init {
        _currentPosition.value = 0
    }

    fun setRecommendedItems(list: List<RecommendedItem>){
        _recommendedItemList.value = list
    }

    fun setCurrentPosition(position: Int){
        _currentPosition.value = position
    }

    fun getcurrentPosition() = currentPosition.value
}