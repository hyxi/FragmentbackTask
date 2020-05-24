package com.example.user.fragmentbacktask.kotlin.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation

//class RoundedCornersTransformation(context: Context,
//                                   dp: Int = 4,
//                                   other:Int?,
//                                   centerCrop: Boolean? = false) : BitmapTransformation(context, ) {
//
//    var radius: Float = 0f
//
//    init {
//        radius = Resources.getSystem().displayMetrics.density * dp
//    }
//
//    override fun getId(): String {
//        return javaClass.name + Math.round(0f)
//    }
//
//    override fun transform(pool: BitmapPool?, toTransform: Bitmap?, outWidth: Int, outHeight: Int): Bitmap? {
//        toTransform?.run{
//            var result: Bitmap? = pool?.get(width, height, Bitmap.Config.ARGB_8888)
//            if (result == null) {
//                result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//            }
//
//            val canvas = Canvas(result!!)
//            val paint = Paint()
//            paint.shader = BitmapShader(this, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
//            paint.isAntiAlias = true
//            val rectF = RectF(0f, 0f, width.toFloat(), height.toFloat())
//            canvas.drawRoundRect(rectF, radius, radius, paint)
//        }
//        return null
//    }
//}