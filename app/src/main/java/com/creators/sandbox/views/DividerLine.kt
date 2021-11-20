package com.creators.sandbox.views


import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.creators.sandbox.R

class DividerLine @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    private val scale: Float = resources.displayMetrics.density

    private val offsetType: Int?
    private val offsetPercentageInt: Float?
    private var offset: Float = 0f
    private var verticalOrientation: Boolean = false
    private var lineStrokeDip: Float
    private var dividerLineColor: Int = 0

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    override fun onSizeChanged(newWidth: Int, newHeight: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(newWidth, newHeight, oldWidth, oldHeight)
        if (offsetPercentageInt != null) {
            offset = (offsetPercentageInt / 100f) * width
        }
    }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DividerLine)
        offsetType = typedArray.getInt(R.styleable.DividerLine_offsetType, 0)
        offsetPercentageInt = typedArray.getFloat(R.styleable.DividerLine_offsetPercentageInt, 0f)
        dividerLineColor = typedArray.getColor(R.styleable.DividerLine_dividerLineColor, 0)
        lineStrokeDip = typedArray.getFloat(R.styleable.DividerLine_lineStrokeDip, 1f) * scale
        verticalOrientation = typedArray.getBoolean(R.styleable.DividerLine_verticalOrientation, false)
        typedArray.recycle()

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.style = Paint.Style.STROKE
        paint.color = dividerLineColor
        paint.strokeWidth = lineStrokeDip
        paint.strokeCap = Paint.Cap.ROUND

        if (verticalOrientation) {
            // Vertical Line - This cannot be in combination with line offset
                canvas.drawLine(0f,0f,0f,height.toFloat(), paint)
        } else {
            // Both sides offset
            if (offsetType == 0) {
                canvas.drawLine(0f + offset, 0f, width - offset, 0f, paint)
            }
            // Left offset
            else if (offsetType == 1) {
                canvas.drawLine(0f + offset, 0f, width.toFloat(), 0f, paint)
            }
            // Right offset
            else if (offsetType == 2) {
                canvas.drawLine(0f, 0f, width - offset, 0f, paint)
            }
            else {
                canvas.drawLine(0f,0f,width.toFloat(),0f,paint)
            }
        }
    }
}