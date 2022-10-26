package com.example.glsurfaceviewdemo2

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.view.View
import androidx.core.view.drawToBitmap

class MyGLSurfaceView(context: Context?, attrs: AttributeSet?) : GLSurfaceView(context, attrs) {
    private val renderer: MyGLRenderer

    var viewLocationArray = IntArray(2)

    //背景View所在屏幕上的坐标
    val decorViewLocationArray = IntArray(2)
    val op = BitmapFactory.Options()

    companion object{
        var mRootView: View? = null

        var viewBitmap: Bitmap? = null
        var rootViewBitmap: Bitmap? = null
        var bitmap:Bitmap?=null
    }

    init {
        setEGLContextClientVersion(2)
        //设置背景色 via：https://blog.csdn.net/weixin_35857552/article/details/117508667
        //PS:边界有小黑边，这里先设置成透明 后面优化处理
        //https://blog.csdn.net/lkl22/article/details/88776952
        setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        setZOrderOnTop(true)
        holder.setFormat(PixelFormat.TRANSLUCENT)

        renderer = MyGLRenderer()
        setRenderer(renderer)
        renderMode = RENDERMODE_WHEN_DIRTY

        bitmap = BitmapFactory.decodeResource(context?.resources, R.drawable.img)
        renderer.setImageBitmap(bitmap)
        post {
            loopBitmap()
        }

    }

    fun cutView04(): Bitmap? {
        val decorView = this.rootView
        val viewBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        val viewCanvas = Canvas(viewBitmap)
        decorView.draw(viewCanvas)
        return viewBitmap
    }

    private fun cutView(): Bitmap? {

        //获取根布局
        mRootView = this.rootView
        rootViewBitmap = mRootView?.drawToBitmap(Bitmap.Config.ARGB_8888)
        //获取需要截图部分的在屏幕上的坐标(view的左上角坐标）
        getLocationOnScreen(viewLocationArray)
        //获取背景View所在屏幕上的坐标
        mRootView?.getLocationOnScreen(decorViewLocationArray)
        //从屏幕整张图片中截取指定区域
        viewBitmap = Bitmap.createBitmap(
            rootViewBitmap!!,
            viewLocationArray[0] - decorViewLocationArray[0],
            viewLocationArray[1] - decorViewLocationArray[1],
            width,//需要截取的长和宽
            height
        )
        return viewBitmap
    }

    var isStop = false
    override fun onPause() {
        super.onPause()
        isStop = true
    }

    override fun onResume() {
        super.onResume()
//        refreshBitmap()
        isStop = false
    }


    fun loopBitmap(){
        bitmap = cutView()
        renderer.setImageBitmap(bitmap)
        requestRender()
        postDelayed({
            loopBitmap()
        }, 16)
    }

    fun refreshBitmap() {
//        Log.d("GLSurfaceView", "refreshBitmap")
        bitmap = cutView()
        postDelayed({
            renderer.setImageBitmap(bitmap)
            //触发 onDrawFrame
            requestRender()
        }, 16)
    }

}