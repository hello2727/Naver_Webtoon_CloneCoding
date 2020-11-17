package com.example.android.webtoon.webtoon.viewer

import android.content.Context
import android.graphics.Matrix
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.Nullable

class ImageViewer : View, View.OnTouchListener{
    lateinit var paint: Paint
    lateinit var matrix2 : Matrix

    constructor(context: Context) : super(context) {
        init(context)
    }
    constructor(context: Context, @Nullable attrs : AttributeSet) : super(context, attrs){
        init(context)
    }

    fun init(context: Context){
        paint = Paint()
        matrix2 = Matrix()
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        TODO("Not yet implemented")
    }
}