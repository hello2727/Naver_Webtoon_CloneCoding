package com.example.android.webtoon.webtoon.imageViewer

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.ScrollView
import com.example.android.webtoon.R

class Viewer1 @JvmOverloads constructor(
    context: Context,
    attrs : AttributeSet
): View(context, attrs) {
//    var enableScrolling = true

//    lateinit var mBitmap : Bitmap
    var mImageWidth = 0
    var mImageHeight = 0
    val mMinZoom = 1.0f
    val mMaxZoom = 2.0f
    var mScaleFactor = 1f
//    var mScaleGestureDetector : ScaleGestureDetector
//    val NONE = 0
//    val PAN = 1
//    val ZOOM = 2
//    var mEventState = 0
//    var mStartX = 0f
//    var mStartY = 0f
//    var mTranslateX = 0f
//    var mTranslateY = 0f
//    var mPreviousTranslateX = 0f
//    var mPreviousTranslateY = 0f

//    var imagePath : String? = null
    var imagePath : Int = 0
    var paint : Paint
    lateinit var cacheBitmap : Bitmap
    lateinit var cacheCanvas : Canvas
//
//    class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
//        override fun onScale(detector: ScaleGestureDetector): Boolean {
//            mScaleFactor *= detector.scaleFactor
//            mScaleFactor = Math.max(mMinZoom, Math.min(mMaxZoom, mScaleFactor))
//            return super.onScale(detector)
//        }
//    }
//
//        mScaleGestureDetector = ScaleGestureDetector(getContext(), ScaleListener())
//
//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        when(event.action) {
//            MotionEvent.ACTION_DOWN -> {
//                mEventState = PAN
//                mStartX = event.x - mPreviousTranslateX
//                mStartY = event.y - mPreviousTranslateY
//            }
//            MotionEvent.ACTION_UP -> {
//                mEventState = NONE
//                mPreviousTranslateX = mTranslateX
//                mPreviousTranslateY = mTranslateY
//            }
//            MotionEvent.ACTION_MOVE -> {
//                mTranslateX = event.x - mStartX
//                mTranslateY = event.y - mStartY
//            }
//            MotionEvent.ACTION_POINTER_DOWN -> {
//                mEventState = ZOOM
//            }
//        }
//        mScaleGestureDetector.onTouchEvent(event)
//        if((mEventState == PAN && mScaleFactor != mMinZoom) || mEventState == ZOOM) {
//            invalidate()
//            requestLayout()
//        }
//        return true
//    }
//
//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//
//        var imageWidth = MeasureSpec.getSize(widthMeasureSpec)
//        var imageHeight = MeasureSpec.getSize(heightMeasureSpec)
//        var scaledWidth = Math.round(mImageWidth * mScaleFactor)
//        var scaledHeight = Math.round(mImageHeight * mScaleFactor)
//
//        setMeasuredDimension(
//            Math.min(imageWidth, scaledWidth),
//            Math.min(imageHeight, scaledHeight)
//        )
//    }

    /* 초기화 */
    init {
        paint = Paint()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if(cacheBitmap != null){
            canvas.drawBitmap(cacheBitmap, 0f, 0f, null)
        }
//        canvas.save()
//        canvas.scale(mScaleFactor, mScaleFactor)
//        if((mTranslateX * -1) < 0) {
//            mTranslateX = 0F
//        }else if((mTranslateX * -1) > mImageWidth * mScaleFactor - width) {
//            mTranslateX = (mImageWidth * mScaleFactor - width) * -1
//        }
//        if((mTranslateY * -1) < 0) {
//            mTranslateY = 0F
//        }else if((mTranslateY * -1) > mImageHeight * mScaleFactor - height) {
//            mPreviousTranslateY = (mImageHeight * mScaleFactor - height) * -1
//        }
//        canvas.translate(mTranslateX/mScaleFactor, mTranslateY/mScaleFactor)
//        canvas.drawBitmap(mBitmap, 0f, 0f, null)
//        canvas.restore()
    }
    fun createCacheBitmap(w : Int, h: Int){
        Log.i("Height", measuredHeight.toString())
        Log.i("Width", measuredWidth.toString())
        cacheBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        cacheCanvas = Canvas()
        cacheCanvas.setBitmap(cacheBitmap)
    }
    fun Drawing() {
        var bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.sample6)
        cacheCanvas.drawBitmap(bitmap, 100f, 100f, paint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        createCacheBitmap(w, h)
        Drawing()
    }
}
