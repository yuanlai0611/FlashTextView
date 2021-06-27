package com.example.flashtextview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt

class FlashTextView(ctx: Context, attr: AttributeSet? = null, def: Int = 0) : View(ctx, attr, def) {

    private val textPaint = TextPaint()
    private var text: CharSequence = ""
    private var textLayout: Layout? = null

    @ColorInt
    private var textColor = Color.BLACK
    private var textSize = 60F

    private var updateTextLayout = false
    private var oldDesiredWidth = 0

    fun setText(text: CharSequence) {
        this.text = text
        updateTextPaint()
        updateTextLayout = true
        requestLayout()
    }

    fun setTextColor(@ColorInt textColor: Int) {
        this.textColor = textColor
        updateTextPaint()
        invalidate()
    }

    fun setTextSize(textSize: Float) {
        this.textSize = textSize
        updateTextPaint()
        updateTextLayout = true
        requestLayout()
    }

    private fun updateTextPaint() {
        textPaint.color = textColor
        textPaint.textSize = textSize
    }

    private fun makeTextLayout(desiredWidth: Int) {
        if (!updateTextLayout && desiredWidth == oldDesiredWidth) {
            return
        }
        val newTextLayout =  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder.obtain(text, 0, text.length, textPaint, desiredWidth).build()
        } else {
            StaticLayout(
                text,
                textPaint,
                desiredWidth,
                Layout.Alignment.ALIGN_NORMAL,
                0.0F,
                0.0F,
                false
            )
        }
        oldDesiredWidth = desiredWidth
        updateTextLayout = false
        textLayout = newTextLayout
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width: Int
        val height: Int

        makeTextLayout(widthSize)
        width = if (widthMode == MeasureSpec.EXACTLY) {
            widthSize
        } else {
            desired(textLayout)
        }

        height = if (heightMode == MeasureSpec.EXACTLY) {
            heightSize
        } else {
            textLayout?.height ?: 0
        }
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.save()
        canvas?.translate(paddingLeft.toFloat(), paddingTop.toFloat())
        textLayout?.draw(canvas)
        canvas?.restore()
    }

}