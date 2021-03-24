package com.example.android.webtoon.view

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.android.webtoon.model.remote.webtoonCuts
import com.example.android.webtoon.view.adapter.EpisodeAdapter
import kotlinx.android.synthetic.main.item_layout_cutlist.view.*
import org.jetbrains.anko.doAsync
import java.io.IOException
import java.net.MalformedURLException
import java.net.SocketTimeoutException
import java.net.URL

class CustomView_webtoonRoundN : View {
    lateinit var rvManager: RecyclerView.LayoutManager

    lateinit var mCanvas : Canvas
//    lateinit var mInitBitmap : Bitmap

    var cutList : ArrayList<webtoonCuts> = arrayListOf()

    var IsItZoomIn = false
    val mMinZoom = 1.0f
    val mMaxZoom = 2.0f

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
//        LayoutInflater.from(context).inflate(R.layout.item_layout_cutlist, this, true)

        // 웹툰컷 리스트화하기
//        setRV()


    }

    fun setCuts(imgPath : String){
        cutList.add(webtoonCuts(imgPath))
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
//    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
//        super.onSizeChanged(w, h, oldw, oldh)
//
//        mInitBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
//        mCanvas = Canvas()
//        mCanvas.setBitmap(mInitBitmap)
//    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if(!cutList.isEmpty()){
            Log.d("배열 상태", "컷툰 리스트 들어있음")
            var x = 0f
            var y = 0f

            for(cut in cutList){
                Log.d("이미지소스 가져오기", "${cut.iv_cut} ${cutList.size}")
                var imgPath = cut.iv_cut

                //var glideUrl = GlideUrl(imgPath, LazyHeaders.Builder().addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit / 537.36(KHTML, like Gecko) Chrome  47.0.2526.106 Safari / 537.36").build())
                var url = URL(imgPath)
                doAsync {
                    try{
                        var bitmap : Bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    }catch (e : MalformedURLException){
                        e.printStackTrace()
                    }catch (e : SocketTimeoutException){
                        e.printStackTrace()
                    }catch (e : IOException){
                        e.printStackTrace()
                    }catch (e : Exception){
                        e.printStackTrace()
                    }
                }
//                    canvas?.drawBitmap(bitmap, x, y, null)
//                    bitmap.recycle()
//
//                x = bitmap.height.toFloat()
            }
        }else{
            Log.d("배열 상태", "컷툰 리스트 없음")
//            if(mInitBitmap != null){
//                canvas?.drawBitmap(mInitBitmap, 0f, 0f, null)
//            }
        }
    }
}
