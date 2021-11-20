package com.example.sandbox.views


import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.example.sandbox.R

class ProfilePicView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    fun setHasProRing (hasProRing: Boolean) {
        this.hasProRing = hasProRing
        invalidate()
    }
    fun setHasThumbsUp (hasThumbsUp: Boolean) {
        this.hasThumbsup = hasThumbsUp
        invalidate()
    }

    private var colors = intArrayOf(
        -0x23eceb, // red
        -0x040cfc, // yellow
        -0x040cfc, // yellow
        -0x000000, // white
        -0x040cfc, // yellow
        -0x040cfc, // yellow
        -0x23eceb, // red
    )

    var positions = floatArrayOf(0.0f, 0.3f, 0.4f,  0.5f, 0.6f, 0.7f , 1f)

    private var imageRadius : Float = 0f
    private var whiteSpaceRadius: Float = 0f
    private var viewRadius : Float = 0f
    private var graySpaceRadius : Float = 0f
    private var viewCenter : Float = 0f

    private var whiteSpaceStroke = 0f
    private var proRingStroke = 0f
    private var graySpaceStroke = 0f

    private var proRing = BitmapFactory.decodeResource(resources,R.drawable.proring)
    private var proStatusThumb = BitmapFactory.decodeResource(context.resources, R.drawable.prostatusthumb)
    private var proRingShader : Shader = SweepGradient(viewCenter,viewCenter, colors, positions )

    private var circlePath = Path()

    private var hasProRing: Boolean
    private var hasThumbsup: Boolean


    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val clearPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        isAntiAlias = true
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    override fun onSizeChanged(newWidth: Int, newHeight: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(newWidth, newHeight, oldWidth, oldHeight)
        viewRadius = (width.coerceAtMost(height) / 2).toFloat()
        imageRadius = (viewRadius - (viewRadius/7.5f))
        whiteSpaceRadius = imageRadius + (viewRadius/24f)
        graySpaceRadius = viewRadius - 1f
        viewCenter = viewRadius


        whiteSpaceStroke = whiteSpaceRadius - imageRadius
        proRingStroke = graySpaceRadius - whiteSpaceRadius
        graySpaceStroke = viewRadius - graySpaceRadius

        proRing = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources,R.drawable.proring),width,height,false)
        proStatusThumb = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources,R.drawable.prostatusthumb),width/3,height/3,false)
    }
    init {
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.ProfilePicView)
        hasProRing = typedArray.getBoolean(R.styleable.ProfilePicView_hasProRing,false)
        hasThumbsup = typedArray.getBoolean(R.styleable.ProfilePicView_hasThumbsUp,false)
        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//
//        // Clipping image
//        circlePath.reset()
//        circlePath.addCircle(viewCenter, viewCenter, whiteSpaceRadius, Path.Direction.CW)
//        circlePath.fillType = Path.FillType.INVERSE_WINDING
//        canvas.drawPath(circlePath, clearPaint)

        // Creating circular transparency
        canvas.save()
        setLayerType(LAYER_TYPE_HARDWARE, null)
        circlePath.rewind()
        circlePath.addCircle(viewCenter,viewCenter,viewRadius,Path.Direction.CW)
        circlePath.fillType = Path.FillType.INVERSE_WINDING
        canvas.clipPath(circlePath)
        canvas.drawRect(0f,0f,width.toFloat(),height.toFloat(),clearPaint)
        canvas.restore()

        if (hasProRing) {
            canvas.save()
            // Adding white space
            circlePath.reset()
            circlePath.addCircle(viewCenter, viewCenter, whiteSpaceRadius - (whiteSpaceStroke/2), Path.Direction.CW)
            circlePath.fillType = Path.FillType.WINDING
            paint.color = Color.WHITE
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = whiteSpaceStroke
            canvas.drawPath(circlePath, paint)

            // Adding proRing effect
            circlePath.rewind()
            circlePath.addCircle(viewCenter, viewCenter, graySpaceRadius - (proRingStroke/2), Path.Direction.CW)
            circlePath.fillType = Path.FillType.WINDING
            paint.strokeWidth = proRingStroke
            paint.shader = proRingShader
            canvas.drawPath(circlePath, paint)

            // Adding thin gray outline
            circlePath.rewind()
            circlePath.addCircle(viewCenter,viewCenter,viewRadius - (graySpaceStroke/2),Path.Direction.CW)
            circlePath.fillType = Path.FillType.WINDING
            paint.color = Color.GRAY
            paint.shader = null
            paint.strokeWidth = graySpaceStroke
            canvas.drawPath(circlePath, paint)

            canvas.restore()
        } else {

            canvas.save()
            paint.shader = null

            // Adding white space
            circlePath.reset()
            circlePath.addCircle(viewCenter, viewCenter, whiteSpaceRadius + 2f, Path.Direction.CW)
            circlePath.fillType = Path.FillType.INVERSE_WINDING
            canvas.clipPath(circlePath)
            paint.color = Color.WHITE
            paint.style = Paint.Style.FILL
            canvas.drawCircle(viewCenter,viewCenter,viewRadius-graySpaceStroke/2,paint)
            canvas.restore() // Covering the pixelated part of img that was clipped
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 3f
            canvas.drawCircle(viewCenter,viewCenter,whiteSpaceRadius + 2f,paint)


            // Adding thin gray outline
            canvas.save()
            circlePath.rewind()
            circlePath.addCircle(viewCenter,viewCenter,viewRadius - graySpaceStroke,Path.Direction.CW)
            circlePath.fillType = Path.FillType.WINDING
            paint.style = Paint.Style.STROKE
            paint.color = Color.LTGRAY
            paint.strokeWidth = graySpaceStroke*2
            canvas.drawPath(circlePath, paint)
            canvas.restore()
        }


        if (hasThumbsup) {
            paint.style = Paint.Style.FILL
            canvas.drawBitmap(proStatusThumb, width - proStatusThumb.width.toFloat(), height - proStatusThumb.height.toFloat(), paint)
        }
    }
}