package com.example.android.webtoon.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.webtoon.R
import com.example.android.webtoon.model.remote.webtoonCuts
import com.example.android.webtoon.view.adapter.EpisodeAdapter
import kotlinx.android.synthetic.main.item_layout_cutlist.view.*
import org.jetbrains.anko.doAsync
import java.net.URL

class CustomView_webtoonRoundN : LinearLayout {
    lateinit var rvManager: RecyclerView.LayoutManager

    lateinit var mCanvas : Canvas
    lateinit var mInitBitmap : Bitmap

    var cutList : ArrayList<webtoonCuts> = arrayListOf()

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

    private fun init(context: Context?) {
        LayoutInflater.from(context).inflate(R.layout.item_layout_cutlist, this, true)

        // 웹툰컷 리스트화하기
        setRV()
    }

    private fun setRV(){
        val rvAdapter = EpisodeAdapter(context, cutList) { ListItem ->
        }
        rvManager = LinearLayoutManager(context)

        rv_weebtoonCuts.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = rvManager

            // specify an viewAdapter (see also next example)
            adapter = rvAdapter

            setOnTouchListener(object : OnTouchListener{
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    when(event?.action){
                        MotionEvent.ACTION_DOWN -> {
                            Log.d("누르기", "success")
                        }
                    }

                    return true
                }
            })
        }
    }

    fun renewRV(imgPath : String){
        cutList.add(webtoonCuts(imgPath))

        // 웹툰컷 리스트화 갱신
        val rvAdapter = EpisodeAdapter(context, cutList) { ListItem ->
        }
        rv_weebtoonCuts.apply {
            adapter = rvAdapter
        }
    }

    //뷰에 사이즈가 정해졌을 때 만들어지는 함수(메모리에 비트맵 객체 하나 만들어줌)
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mInitBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if(mInitBitmap != null){
            canvas?.drawBitmap(mInitBitmap, 0f, 0f, null)
        }
    }
}
