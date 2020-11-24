package com.example.android.webtoon.webtoon

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.PointF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.webtoon.R
import com.example.android.webtoon.webtoon.adapter.EpisodeAdapter
import com.example.android.webtoon.webtoon.data.contentUnit

class EpisodeActivity : AppCompatActivity() {
    var doubleClickFlag = 0
    var CLICK_DELAY = 1000L;

    lateinit var scrollView : ScrollView
    var mScale = 1f
    lateinit var mScaleDetector : ScaleGestureDetector
    lateinit var gestureDetector: GestureDetector

    lateinit var mBitmap : Bitmap
    var mImageWidth = 0
    var mImageHeight = 0
    val mMinZoom = 1.0f
    val mMaxZoom = 2.0f
    lateinit var mScaleGestureDetector : ScaleGestureDetector
    val NONE = 0
    val PAN = 1
    val ZOOM = 2
    var mEventState = 0
    var mStartX = 0f
    var mStartY = 0f
    var mTranslateX = 0f
    var mTranslateY = 0f
    var mPreviousTranslateX = 0f
    var mPreviousTranslateY = 0f

    private lateinit var rv_listOfcontent : RecyclerView
    private lateinit var rvManager: RecyclerView.LayoutManager

    var episodeUnit = arrayListOf<contentUnit>(
        contentUnit(""),
        contentUnit(""),
        contentUnit(""),
        contentUnit(""),
        contentUnit(""),
        contentUnit("")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode)

        /* jpg 이어붙여 한 회차 만들기 */
        rvManager = LinearLayoutManager(this)
        val rvAdapter = EpisodeAdapter(this, episodeUnit) { contentUnit ->
            /* 더블클릭 이벤트 */
            DoubleClick()
//            ListItem ->
//            val intent = Intent(this, EpisodeActivity::class.java)
//            startActivity(intent)
//            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        rv_listOfcontent = findViewById<RecyclerView>(R.id.rv_contents).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = rvManager

            // specify an viewAdapter (see also next example)
            adapter = rvAdapter
        }

        /* 이미지 뷰어 */
        scrollView = findViewById(R.id.sv_epi)
        zoomInOut()
    }

    /* 1. , 2. 화면 줌인 및 줌아웃 */
    fun zoomInOut() {
        gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener(){
//            var touchX = 0f
//            var touchY = 0f
//            var dx = 0f
//            var dy = 0f
//
//            override fun onDown(e: MotionEvent): Boolean {
//                when(e.action){
//                    MotionEvent.ACTION_DOWN -> {
//                        touchX = e.x
//                        touchY = e.y
//                    }
//                    MotionEvent.ACTION_MOVE -> {
//                        dx = e.x - touchX
//                        dy = e.y - touchY
//
//                        scrollView.x += dx
//                        scrollView.y += dy
//
//                        touchX = e.x
//                        touchY = e.y
//                    }
//                }
//                return true
//            }
        })
        mScaleDetector = ScaleGestureDetector(this, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
//                mScale *= detector.scaleFactor
//
//                if(mScale < 1f){
//                    mScale = 1f
//                }
//                if(mScale > 2f){
//                    mScale = 2f
//                }
//
//                scrollView.scaleX = mScale
//                scrollView.scaleY = mScale
//                return true
                mScale *= detector.scaleFactor
                mScale = Math.max(mMinZoom, Math.min(mMaxZoom, mScale))
                scrollView.scaleX = mScale
                scrollView.scaleY = mScale
                return true
            }
        })
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        super.dispatchTouchEvent(ev)
        mScaleDetector.onTouchEvent(ev)
        gestureDetector.onTouchEvent(ev)
        return gestureDetector.onTouchEvent(ev)
    }

    /* 화면 더블클릭 이벤트(원본에서 더블클릭하면 확대화면, 확대화면에서 더블클릭하면 원본 화면으로 변환) */
    fun DoubleClick() {
        doubleClickFlag++
        var clickRunnable = object : Runnable {
            override fun run() {
                doubleClickFlag = 0
                //클릭 이벤트
            }
        }
        if(doubleClickFlag == 1){
            Handler().postDelayed(clickRunnable, CLICK_DELAY)
        }else if(doubleClickFlag == 2){
            doubleClickFlag = 0
            //더블클릭 이벤트
            if(scrollView.scaleX > mMinZoom && scrollView.scaleX <= mMaxZoom && scrollView.scaleY > mMinZoom && scrollView.scaleY <= mMaxZoom){
                scrollView.scaleX = mMinZoom
                scrollView.scaleY = mMinZoom
            }else if(scrollView.scaleX == mMinZoom && scrollView.scaleY == mMinZoom){
                scrollView.scaleX = mMaxZoom
                scrollView.scaleY = mMaxZoom
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var touchX = 0f
        var touchY = 0f
        var dx = 0f
        var dy = 0f
        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                touchX = event.x
                touchY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                dx = event.x - touchX
                dy = event.y - touchY

                scrollView.x += dx
                scrollView.y += dy

                touchX = event.x
                touchY = event.y
            }
        }
        return super.onTouchEvent(event)
    }

    /* 뒤로가기 버튼 이벤트 */
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
