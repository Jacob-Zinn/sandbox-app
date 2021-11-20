package com.creators.sandbox.views


import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class CircleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var imageRadius : Float = 0f
    private var viewCenter : Float = 0f
    private var circlePath = Path()

    private val clearPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    override fun onSizeChanged(newWidth: Int, newHeight: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(newWidth, newHeight, oldWidth, oldHeight)
        imageRadius = (width.coerceAtMost(height) / 2).toFloat()
        viewCenter = imageRadius
    }

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        clearPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.save()
        circlePath.addCircle(viewCenter,viewCenter,imageRadius,Path.Direction.CW)
        circlePath.fillType = Path.FillType.INVERSE_WINDING

        canvas.drawPath(circlePath,clearPaint)
        canvas.restore()
    }
}