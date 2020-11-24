package com.example.android.webtoon.webtoon.imageViewer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.ScrollView

class Viewer1 : ScrollView {
    var enableScrolling = true

//    lateinit var mBitmap : Bitmap
    var mImageWidth = 0
    var mImageHeight = 0
    val mMinZoom = 1.0f
    val mMaxZoom = 2.0f
    var mScaleFactor = 1f
//    var mScaleGestureDetector : ScaleGestureDetector
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
//
//    class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
//        override fun onScale(detector: ScaleGestureDetector): Boolean {
//            mScaleFactor *= detector.scaleFactor
//            mScaleFactor = Math.max(mMinZoom, Math.min(mMaxZoom, mScaleFactor))
//            return super.onScale(detector)
//        }
//    }
//
    constructor(context: Context) : super(context) {

    }
    constructor(context: Context, attrs : AttributeSet) : super(context, attrs) {
//        mScaleGestureDetector = ScaleGestureDetector(getContext(), ScaleListener())
    }
    constructor(context: Context, attrs: AttributeSet, defStyle : Int) : super(context, attrs, defStyle) {

    }
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
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var imageWidth = MeasureSpec.getSize(widthMeasureSpec)
        var imageHeight = MeasureSpec.getSize(heightMeasureSpec)
        var scaledWidth = Math.round(mImageWidth * mScaleFactor)
        var scaledHeight = Math.round(mImageHeight * mScaleFactor)

        setMeasuredDimension(
            Math.min(imageWidth, scaledWidth),
            Math.min(imageHeight, scaledHeight)
        )
    }
//
//    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//
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
//    }
//
//    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
//        super.onSizeChanged(w, h, oldw, oldh)
//    }
}
