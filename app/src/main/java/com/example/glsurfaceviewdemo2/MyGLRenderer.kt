package com.example.glsurfaceviewdemo2

import android.graphics.Bitmap
import android.opengl.EGLConfig
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.util.Log
import javax.microedition.khronos.opengles.GL10

class MyGLRenderer : GLSurfaceView.Renderer {

    private lateinit var bitmapSquare: BitmapSquare
    // 纹理ID
    private var glTextureId = 0
    private var bitmap: Bitmap? = null



    override fun onSurfaceCreated(gl: GL10?, config: javax.microedition.khronos.egl.EGLConfig?) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        bitmapSquare = BitmapSquare()
        // 创建纹理
//        glTextureId = OpenGLUtils.createTexture(bitmap, GLES20.GL_NEAREST, GLES20.GL_LINEAR,
//            GLES20.GL_CLAMP_TO_EDGE, GLES20.GL_CLAMP_TO_EDGE)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        Log.d("GLSurfaceView","onDrawFrame...")
        // 创建纹理
//        glTextureId = OpenGLUtils.createTexture(bitmap, GLES20.GL_NEAREST, GLES20.GL_LINEAR,
//            GLES20.GL_CLAMP_TO_EDGE, GLES20.GL_CLAMP_TO_EDGE)
//
        glTextureId = OpenGLUtils.loadTexture(glTextureId,bitmap)
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        bitmapSquare.draw(glTextureId)
    }

    fun setImageBitmap(bitmap: Bitmap?) {
        Log.d("GLSurfaceView","setImageBitmap")
        if (bitmap==null){
            return
        }
        this.bitmap = bitmap
    }
}