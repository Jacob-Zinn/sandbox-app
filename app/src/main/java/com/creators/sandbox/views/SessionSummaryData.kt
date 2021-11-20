package com.creators.sandbox.views


import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.creators.sandbox.R
import com.creators.sandbox.util.ImperialMetricConversions
import com.creators.sandbox.util.Rounding

class SessionSummaryData @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    fun setSessionSummaryData(
        _duration: String,
        _distance: Float,
        _numLaps: Int,
        _avgSpd: Float,
        _isImperial: Boolean
    ) {
        duration = _duration
        distance = _distance
        numLaps = _numLaps
        avgSpd = _avgSpd
        isImperial = _isImperial
        invalidate()
    }

    // Data Fields
    private var duration: String = "00:00"
    private var distance: Float = 0f
    private var numLaps: Int = 0
    private var avgSpd: Float = 0f
    private var isImperial: Boolean = true

    private var largeTextSize: Float = 0f
    private var smallTextSize: Float = 0f
    private var lineSpaceGap: Float = 0f
    private var xPos1: Float = 0f
    private var xPos2: Float = 0f

    private var lineStroke: Float
    private var dividerLineColor: Int = 0


    private var redColor = 0
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    override fun onSizeChanged(newWidth: Int, newHeight: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(newWidth, newHeight, oldWidth, oldHeight)
        largeTextSize = height * .21f
        smallTextSize = height * .12f
        lineSpaceGap = height * .1f

        xPos1 = width*.30f
        xPos2 = width*.80f
    }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SessionSummaryData)
        dividerLineColor = typedArray.getColor(R.styleable.SessionSummaryData_summaryLineColor, 0)
        lineStroke = typedArray.getFloat(R.styleable.SessionSummaryData_summaryLineStroke, 1f)
        redColor = typedArray.getColor(R.styleable.SessionSummaryData_summaryRedColor,0)
        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw large text
        paint.style = Paint.Style.FILL
        paint.textSize = largeTextSize
        paint.textAlign = Paint.Align.CENTER
        paint.color = redColor
        canvas.drawText(numLaps.toString(),xPos2,largeTextSize,paint)
        paint.color = Color.DKGRAY
        canvas.drawText(duration,xPos1, largeTextSize, paint)
        if (isImperial) {
            canvas.drawText(ImperialMetricConversions.feetToMiOneDecimal(distance),xPos1,height*.5f+lineSpaceGap+largeTextSize,paint)
            canvas.drawText(Rounding.roundToOneDecimalReturnString(avgSpd),xPos2,height*.5f+lineSpaceGap+largeTextSize,paint)
        } else {
            canvas.drawText(ImperialMetricConversions.feetToKmOneDecimal(distance),xPos1,height*.5f+lineSpaceGap+largeTextSize,paint)
            canvas.drawText(ImperialMetricConversions.mphToKphOneDecimal(avgSpd),xPos2,height*.5f+lineSpaceGap+largeTextSize,paint)
        }

        // Draw smaller info text
        paint.textSize = smallTextSize
        canvas.drawText(resources.getString(R.string.duration),xPos1,height*.5f-lineSpaceGap,paint)
        canvas.drawText(resources.getString(R.string.laps),xPos2,height*.5f-lineSpaceGap,paint)
        if (isImperial) {
             canvas.drawText(resources.getString(R.string.miles),xPos1,height.toFloat()-8f,paint)
             canvas.drawText(resources.getString(R.string.avg_mph),xPos2,height.toFloat()-8f,paint)
        } else {
            canvas.drawText(resources.getString(R.string.kilometers),xPos1,height.toFloat()-8f,paint)
            canvas.drawText(resources.getString(R.string.avg_kph),xPos2,height.toFloat()-8f,paint)
        }

        // Draw dividing lines
        paint.style = Paint.Style.STROKE
        paint.color = dividerLineColor
        paint.strokeWidth = lineStroke
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawLine((xPos1 +xPos2)/2f, 0f, (xPos1 +xPos2)/2f, height.toFloat() -5f, paint)
        canvas.drawLine(0f,height/2f,width.toFloat(),height/2f,paint)


    }
}