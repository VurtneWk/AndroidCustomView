package com.vurtnewk.chapter04

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * createTime:  2024/12/31 01:33
 * author:      vurtnewk
 * description:
 */
class ColorTrackTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {

    var currentProgress = 0F
        set(value) {
            field = value
            invalidate()
        }
    var direction = Direction.LEFT_TO_RIGHT

    //不变色的画笔
    private val mOriginPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    //变色的画笔
    private val mChangePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mTextBoundsRect = Rect()

    private val mRect = Rect()
    private var drawX = 0F
    private var baseline = 0F

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView)
        val originColor = typedArray.getColor(R.styleable.ColorTrackTextView_originColor, Color.BLACK)
        val changeColor = typedArray.getColor(R.styleable.ColorTrackTextView_changeColor, Color.RED)
        typedArray.recycle()

        initPaint(mOriginPaint, originColor)
        initPaint(mChangePaint, changeColor)
    }

    private fun initPaint(paint: Paint, color: Int) {
        paint.color = color
        paint.isDither = true
        paint.textSize = textSize
    }

    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
        if (drawX == 0F || baseline == 0F) {
            paint.getTextBounds(text.toString(), 0, text.length, mTextBoundsRect)
            drawX = width / 2 - mTextBoundsRect.width() / 2F
            val fontMetrics = paint.fontMetrics
            baseline = height / 2 - (fontMetrics.descent + fontMetrics.ascent) / 2F
        }

        val middle = (width * currentProgress).toInt()
        if (direction == Direction.LEFT_TO_RIGHT) {
            drawText(mChangePaint, canvas, 0, middle)
            drawText(mOriginPaint, canvas, middle, width)
        } else {//右到左
            drawText(mChangePaint, canvas, width - middle, width)
            drawText(mOriginPaint, canvas, 0, width - middle)
        }
    }

    private fun drawText(paint: Paint, canvas: Canvas, start: Int, end: Int) {
        canvas.save()
        mRect.left = start
        mRect.top = 0
        mRect.right = end
        mRect.bottom = height
        canvas.clipRect(mRect)
        canvas.drawText(text.toString(), drawX, baseline, paint)
        canvas.restore()
    }


    enum class Direction {
        LEFT_TO_RIGHT, RIGHT_TO_LEFT
    }
}

