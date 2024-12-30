package com.vurtnewk.chapter03

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.WindowInsetsAnimation.Bounds
import com.orhanobut.logger.Logger

/**
 * createTime:  2024/12/30 07:59
 * author:      vurtnewk
 * description: 仿QQ运动步数
 *
 */
class QQStepView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mOuterColor: Int = Color.BLUE
    private var mInnerColor: Int = Color.RED
    private var mBorderWidth = 20 //px
    private var mStepTextSize = 16
    private var mStepTextColor = Color.RED
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    //和Rect的区别是RectF是浮点数矩形
    private val mRectF = RectF()
    var mMaxStep = 100
    var mCurrentStep = 0
        set(value) {
            field = value
            invalidate()
        }
    private val mBounds = Rect()

    init {
        val obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.QQStepView)
        with(obtainStyledAttributes) {
            mOuterColor = getColor(R.styleable.QQStepView_outerColor, mOuterColor)
            mInnerColor = getColor(R.styleable.QQStepView_innerColor, mInnerColor)
            mBorderWidth = getDimension(R.styleable.QQStepView_borderWidth, mBorderWidth.toFloat()).toInt()
            mStepTextSize = getDimensionPixelSize(R.styleable.QQStepView_stepTextSize, mStepTextSize)
            mStepTextColor = getColor(R.styleable.QQStepView_stepTextColor, mStepTextColor)
        }
        obtainStyledAttributes.recycle()
        Logger.d("mPaint.strokeWidth => ${mPaint.strokeWidth}")

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //宽高可能不一致、可能wrap_content
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        //没做其它判断 只是让view是正方形
        setMeasuredDimension(widthSize.coerceAtMost(heightSize), widthSize.coerceAtMost(heightSize))
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mRectF.top = 0F + mBorderWidth / 2
        mRectF.left = 0F + mBorderWidth / 2
        mRectF.right = width.toFloat() - mBorderWidth / 2
        mRectF.bottom = height.toFloat() - mBorderWidth / 2

        //外部圆
        mPaint.strokeWidth = mBorderWidth.toFloat()
        mPaint.color = mOuterColor
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeCap = Paint.Cap.ROUND
        canvas.drawArc(mRectF, 135F, 270F, false, mPaint)


        //内圆
        if (mCurrentStep == 0) return
        mPaint.color = mInnerColor
        //mCurrentStep / mMaxStep 不转换成Float的话结果是0
        val sweepAngle = (mCurrentStep / mMaxStep.toFloat()) * 270
        canvas.drawArc(mRectF, 135F, sweepAngle, false, mPaint)

        //文字
        mPaint.color = mStepTextColor
        mPaint.textSize = mStepTextSize.toFloat()
        mPaint.strokeWidth = 0F
        val stepText = mCurrentStep.toString()
        mPaint.getTextBounds(stepText, 0, stepText.length, mBounds)
        val dx = width / 2 - mBounds.width() / 2F
        val dy = height / 2 - (mPaint.fontMetrics.ascent + mPaint.fontMetrics.descent) / 2
        canvas.drawText(stepText, dx, dy, mPaint)
    }

}