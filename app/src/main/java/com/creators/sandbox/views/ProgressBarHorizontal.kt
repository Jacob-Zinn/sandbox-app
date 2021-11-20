package com.creators.sandbox.views


import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import com.creators.sandbox.R

class ProgressBarHorizontal @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    private val scale: Float = resources.displayMetrics.density
    private var percentage = 0f

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    fun setPercentage(percentage: Float) {
        this.percentage = percentage
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // grey background
        paint.color = ResourcesCompat.getColor(resources, R.color.grey2,null)
        canvas.drawRoundRect(0f,0f,width.toFloat(), height.toFloat(), 5 * scale, 5 * scale, paint)

        // fill bar
        paint.color = ResourcesCompat.getColor(resources, R.color.red,null)

        val barLengthPercentage: Float = xPosForBarEnd()
        canvas.drawRoundRect(0f,0f,width * barLengthPercentage, height.toFloat(), 5 * scale, 5f * scale, paint)

        // percentage text
        paint.color = Color.WHITE
        paint.textSize = height * .8f
        val percentText: String = getPercentText()
        if (barLengthPercentage > .2) {
            paint.textAlign = Paint.Align.RIGHT
            paint.color = Color.WHITE
            canvas.drawText(percentText, height.toFloat(), width * barLengthPercentage + (.05f * width) , paint)
        } else {
            paint.textAlign = Paint.Align.LEFT
            paint.color = Color.DKGRAY
        }
        canvas.drawText(percentText, height.toFloat(), width * barLengthPercentage  - (.05f * width) , paint)
    }

    private fun xPosForBarEnd(): Float {
        if (percentage == 0f) {
            return 1f
        }
        return percentage
    }

    private fun getPercentText() : String {
        if (percentage == 0f) {
            return "N/A%"
        }
        return "${percentage * 100} %"
    }

}