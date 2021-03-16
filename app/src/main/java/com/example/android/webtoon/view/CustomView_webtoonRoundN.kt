package com.example.android.webtoon.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import com.example.android.webtoon.R

class CustomView_webtoonRoundN : ScrollView {
    lateinit var mCanvas : Canvas
    lateinit var mInitBitmap : Bitmap
    var cutList : ArrayList<Bitmap> = arrayListOf()

    constructor(context: Context?) : super(context){
        // 초기화
        init(context)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        // 초기화
        init(context)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        // 초기화
        init(context)
    }

    private fun init(context: Context?){

    }

    //뷰에 사이즈가 정해졌을 때 만들어지는 함수(메모리에 비트맵 객체 하나 만들어줌)
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mInitBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas()
        mCanvas.setBitmap(mInitBitmap)
        mCanvas.drawColor(Color.CYAN)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if(mInitBitmap != null){
            canvas?.drawBitmap(mInitBitmap, 0f, 0f, null)
        }
    }
}
