package com.vurtnewk.chapter05

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.vurtnewk.base.core.ext.dpToPx
import com.vurtnewk.base.core.ext.spToPx

/**
 * createTime:  2024/12/31 15:50
 * author:      vurtnewk
 * description: 自定义进度条
 */
class VProgressBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var currentProgress = 50
        set(value) {
            if (value < 0) throw IllegalArgumentException("currentProgress must > 0")
            field = value
            invalidate()
        }
    var maxProgress = 100
        set(value) {
            if (value < 0) throw IllegalArgumentException("maxProgress must > 0")
            field = value
        }
    private var mOuterBackground = Color.RED
    private var mInnerBackground = Color.BLUE
    private var mRoundWidth = 10.dpToPx()
    private var mProgressTextSize = 15.spToPx()
    private var mProgressTextColor = Color.RED
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mTextBoundsRect = Rect()

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.VProgressBar)
        mOuterBackground = typedArray.getColor(R.styleable.VProgressBar_outerBackground, mOuterBackground)
        mInnerBackground = typedArray.getColor(R.styleable.VProgressBar_innerBackground, mInnerBackground)
        mRoundWidth = typedArray.getDimension(R.styleable.VProgressBar_roundWidth, mRoundWidth)
        mProgressTextSize = typedArray.getDimension(R.styleable.VProgressBar_progressTextSize, mProgressTextSize)
        mProgressTextColor = typedArray.getColor(R.styleable.VProgressBar_progressTextColor, mProgressTextColor)
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //只保证正方形
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width.coerceAtMost(height), width.coerceAtMost(height))
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mPaint.color = mInnerBackground
        mPaint.strokeWidth = mRoundWidth
        mPaint.style = Paint.Style.STROKE

        val center = width / 2F
        canvas.drawCircle(center, center, center - mRoundWidth / 2, mPaint)

        //进度
        val percent = currentProgress / maxProgress.toFloat()
        mPaint.color = mOuterBackground
        canvas.drawArc(
            0F + mRoundWidth / 2,
            0F + mRoundWidth / 2,
            width.toFloat() - mRoundWidth / 2,
            height.toFloat() - mRoundWidth / 2,
            0F,
            percent * 360F,
            false, mPaint
        )

        //draw text
        mPaint.strokeWidth = 0F
        mPaint.style = Paint.Style.FILL
        mPaint.textSize = mProgressTextSize
        mPaint.color = mProgressTextColor
        val text = "${(percent * 100).toInt()} %"
        mPaint.getTextBounds(text, 0, text.length, mTextBoundsRect)
        val x = width / 2 - mTextBoundsRect.width() / 2F
        val fontMetrics = mPaint.fontMetrics
        val baseline = height / 2 - (fontMetrics.descent + fontMetrics.ascent) / 2
        canvas.drawText(text, x, baseline, mPaint)
    }
}