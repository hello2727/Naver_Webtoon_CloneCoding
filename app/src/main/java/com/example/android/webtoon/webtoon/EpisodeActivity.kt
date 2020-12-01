package com.example.android.webtoon.webtoon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.method.Touch
import android.util.Log
import android.view.*
import android.widget.ScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.webtoon.R
import com.example.android.webtoon.webtoon.adapter.EpisodeAdapter
import com.example.android.webtoon.webtoon.data.contentUnit

class EpisodeActivity : AppCompatActivity() {
    var doubleClickFlag = 0
    var CLICK_DELAY = 1000L;

    lateinit var scrollView: ScrollView
    lateinit var mScaleDetector: ScaleGestureDetector
    lateinit var gestureDetector: GestureDetector
//    lateinit var mTouchListener : Touch
    var mScale = 1.0f
    val mMinZoom = 1.0f
    val mMaxZoom = 2.0f

    var startX = 0.0f
    var startY = 0.0f
    var dx = 0f
    var dy = 0f
    var oldPointerCount = 0

    private lateinit var rv_listOfcontent: RecyclerView
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
        gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent): Boolean {
                startX = e.x
                startY = e.y

                return super.onDown(e)
            }
            override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
                var moveX = -e2.x
                var moveY = e2.y
                oldPointerCount = e2.pointerCount
                if(oldPointerCount == 2){
                    startX = e2.getX(0)
                    startY = e2.getY(0)
                    dx = e2.getX(0) - e2.getX(1)
                    dy = e2.getY(0) - e1.getY(0)

                    Log.d("개", "$oldPointerCount $dx ${e2.getX(0)} ${e2.getX(1)}")
                }

                return super.onScroll(e1, e2, distanceX, distanceY)
            }
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                oldPointerCount = e.pointerCount
                dx = 0f

                Log.d("개", "$oldPointerCount $dx")
                return super.onSingleTapUp(e)
            }

        })
        mScaleDetector = ScaleGestureDetector(this, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
                override fun onScale(detector: ScaleGestureDetector): Boolean {
                    mScale *= detector.scaleFactor

                    mScale = Math.max(mMinZoom, Math.min(mMaxZoom, mScale))
                    scrollView.scaleX = mScale
                    scrollView.scaleY = mScale
                    scrollView.pivotX = startX
                    scrollView.pivotY = startY

                    Log.d("줌인아웃", "$startX")
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
        if (doubleClickFlag == 2 && dx == 0f) {
            doubleClickFlag = 0
            //더블클릭 이벤트
            if (scrollView.scaleX > mMinZoom && scrollView.scaleX <= mMaxZoom && scrollView.scaleY > mMinZoom && scrollView.scaleY <= mMaxZoom) {
                scrollView.scaleX = mMinZoom
                scrollView.scaleY = mMinZoom
                scrollView.pivotX = startX
                scrollView.pivotY = startY
            } else if (scrollView.scaleX == mMinZoom && scrollView.scaleY == mMinZoom) {
                scrollView.scaleX = mMaxZoom
                scrollView.scaleY = mMaxZoom
                scrollView.pivotX = startX
                scrollView.pivotY = startY
            }
        }else{
            Handler().postDelayed(clickRunnable, CLICK_DELAY)
        }
    }

    /* 뒤로가기 버튼 이벤트 */
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}