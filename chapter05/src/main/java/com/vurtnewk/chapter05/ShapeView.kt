package com.vurtnewk.chapter05

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.drawable.shapes.Shape
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.view.View
import kotlin.math.sqrt

/**
 * createTime:  2025/1/1 09:53
 * author:      vurtnewk
 * description:
 */
class ShapeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mCurrentShape = Shape.Circle
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mHandlerChangeMessage = 1
    private val mHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == mHandlerChangeMessage) {
                mCurrentShape = when (mCurrentShape) {
                    Shape.Circle -> Shape.Square
                    Shape.Square -> Shape.Triangle
                    Shape.Triangle -> Shape.Circle
                }
                invalidate()
                sendEmptyMessageDelayed(mHandlerChangeMessage, 1000)
            }
        }
    }
    private val mPath = Path()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //只保证正方形
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width.coerceAtMost(height), width.coerceAtMost(height))
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        when (mCurrentShape) {
            Shape.Circle -> {
                mPaint.color = Color.YELLOW
                val center = width / 2F
                canvas.drawCircle(center, center, center, mPaint)
            }

            Shape.Square -> {
                mPaint.color = Color.BLUE
                canvas.drawRect(0F, 0F, width.toFloat(), width.toFloat(), mPaint)
            }

            Shape.Triangle -> {
                mPaint.color = Color.RED
                //mPath中是否已有图形数据
                if (mPath.isEmpty) {
                    // 计算等边三角形的高度
                    val h = (sqrt(3.0) / 2.0 * width).toFloat()
                    mPath.moveTo(width / 2F, 0F)
                    mPath.lineTo(0F, h)
                    mPath.lineTo(width.toFloat(), h)
                    //如果不用close，需要手动画一条线来封闭图形
                    mPath.close()
                }
                canvas.drawPath(mPath, mPaint)
            }
        }
    }

    fun exchange() {
        mHandler.removeMessages(mHandlerChangeMessage)
        mHandler.sendEmptyMessageDelayed(mHandlerChangeMessage, 1000)
    }

    enum class Shape {
        Circle, Square, Triangle
    }

    override fun onDetachedFromWindow() {
        mHandler.removeMessages(mHandlerChangeMessage)
        super.onDetachedFromWindow()
    }

}