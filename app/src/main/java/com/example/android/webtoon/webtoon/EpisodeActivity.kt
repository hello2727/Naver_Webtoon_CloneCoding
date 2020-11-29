package com.example.android.webtoon.webtoon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.ScrollView
import android.widget.Scroller
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.webtoon.R
import com.example.android.webtoon.webtoon.adapter.EpisodeAdapter
import com.example.android.webtoon.webtoon.data.contentUnit

class EpisodeActivity : AppCompatActivity() {
    var doubleClickFlag = 0
    var CLICK_DELAY = 1000L;

    lateinit var scrollView : ScrollView
    lateinit var mScaleDetector : ScaleGestureDetector
    lateinit var gestureDetector: GestureDetector
    var mScale = 1.0f
    val mMinZoom = 1.0f
    val mMaxZoom = 2.0f

//    lateinit var mBitmap : Bitmap
//    var mImageWidth = 0
//    var mImageHeight = 0
//    var matrix : Matrix = Matrix()
//    var distanceThreshold = 1.5f
//    lateinit var mScaleGestureDetector : ScaleGestureDetector
//    val NONE = 0
//    val PAN = 1
//    val ZOOM = 2
//    var mEventState = 0
//    var scaleRatio = 1.0f
//    var totalScaleRatio = 1.0f
    var startX = 0.0f
    var startY = 0.0f
    var oldDistance = 0.0f
//    var oldPointerCount = 0
    var isScrolling = false
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
            override fun onDown(e: MotionEvent): Boolean {
                startX = e.getX(0)
                startY = e.getY(0)

                return super.onDown(e)
            }
            override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
                if(mScale > 1.0f){
//                    scrollView.translationX = e2.getX(0) - e1.getX(0)
//                    scrollView.translationY = e2.getY(0) - e1.getY(0)
//                    Log.i("크기", scrollView.translationX.toString()+","+scrollView.translationY.toString())
                }

                return super.onScroll(e1, e2, distanceX, distanceY)
            }

            override fun onSingleTapUp(e: MotionEvent): Boolean {
                if(e.getX(0) == 0f && e.getX(0) == 0f){
                    startX = e.getX(1)
                    startY = e.getY(1)
                }

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

//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        var touchX = 0f
//        var touchY = 0f
//        var dx = 0f
//        var dy = 0f
//        when(event.action){
//            MotionEvent.ACTION_DOWN -> {
//                touchX = event.x
//                touchY = event.y
//            }
//            MotionEvent.ACTION_MOVE -> {
//                dx = event.x - touchX
//                dy = event.y - touchY
//
//                scrollView.x += dx
//                scrollView.y += dy
//
//                touchX = event.x
//                touchY = event.y
//            }
//        }
//        return super.onTouchEvent(event)
//    }

    /* 뒤로가기 버튼 이벤트 */
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

//    override fun onTouch(v: View, ev: MotionEvent): Boolean {
//        val action = ev.action
//        var pointerCount = ev.pointerCount
//
//        when(action){
//            MotionEvent.ACTION_DOWN -> {
//                if(pointerCount == 1){
//                    var curX = ev.x
//                    var curY = ev.y
//
//                    startX = curX
//                    startY = curY
//                }else if(pointerCount == 2){
//                    oldDistance = 0.0f
//                    isScrolling = true
//                }
//                return true
//            }
//            MotionEvent.ACTION_MOVE -> {
//                if(pointerCount == 1){
//                    if(isScrolling){
//                        return true
//                    }
//
//                    var curX = ev.x
//                    var curY = ev.y
//
//                    if(startX == 0.0f){
//                        startX = curX
//                        startY = curY
//
//                        return true
//                    }
//
//                    var offsetX = startX - curX
//                    var offsetY = startY - curY
//
//                    if(oldPointerCount == 2){
//
//                    }else{
//                        if(totalScaleRatio > 1.0f){
//                            moveImage(-offsetX, -offsetY)
//                        }
//
//                        startX = curX
//                        startY = curY
//                    }
//                }else if(pointerCount == 2){
//                    var x1 = ev.getX(0)
//                    var y1 = ev.getY(0)
//                    var x2 = ev.getX(1)
//                    var y2 = ev.getY(1)
//
//                    var dx = x1 - x2
//                    var dy = y1 - y2
//                    var distance = (Math.sqrt((dx*dx + dy*dy).toDouble())).toFloat()
//
//                    var outScaleRatio = 0.0f
//                    if(oldDistance == 0.0f){
//                        oldDistance = distance
//                    }
//
//                    if(distance > oldDistance){
//                        if((distance-oldDistance) < distanceThreshold){
//                            return true
//                        }
//
//                        outScaleRatio = scaleRatio + (oldDistance/distance*1.0f)
//                    }else if(distance < oldDistance){
//                        if((oldDistance-distance) < distanceThreshold) {
//                            return true
//                        }
//
//                        outScaleRatio = scaleRatio - (distance/oldDistance*1.0f)
//                    }
//
//                    if(outScaleRatio < mMinZoom || outScaleRatio > mMaxZoom){
//
//                    }else{
//                        scaleImage(outScaleRatio)
//                    }
//
//                    oldDistance = distance
//                }
//
//                oldPointerCount = pointerCount
//            }
//            MotionEvent.ACTION_UP -> {
//                if(pointerCount == 1){
//                    var curX = ev.x
//                    var curY = ev.y
//
//                    var offsetX = startX - curX
//                    var offsetY = startY - curY
//
//                    if(pointerCount == 2){
//
//                    }else{
//                        moveImage(-offsetX, -offsetY)
//                    }
//                }else{
//                    isScrolling = false
//                }
//
//                return true
//            }
//        }
//        return true
//    }
//
//    fun scaleImage(inScaleRatio : Float){
////        matrix.postScale(inScaleRatio, inScaleRatio, bitmapCenterX, bitmapCenterY)
////        matrix.postRotate(0F)
//        scrollView.scaleX = inScaleRatio
//        scrollView.scaleY = inScaleRatio
//        totalScaleRatio = totalScaleRatio * inScaleRatio
//    }
//
//    fun moveImage(offsetX : Float, offsetY : Float){
////        matrix.postTranslate(offsetX, offsetY)
//        scrollView.translationX = offsetX
//        scrollView.translationY = offsetY
//        return true
//    }
}
