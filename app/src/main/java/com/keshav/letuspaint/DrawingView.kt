package com.keshav.letuspaint

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View


class DrawingVeiw(context : Context, attr : AttributeSet) : View(context,attr) {
    private  var mDrawPath :CustomPath? = null
    private  var mCanvasBitmap : Bitmap? = null
    private var mDrawPaint : Paint?= null
    private var mCanvasPaint : Paint? = null
    private var mBrushSize : Float = 0.toFloat()
    private var color  = Color.BLUE
    private var canvas : Canvas? = null
    private val mPaths = ArrayList<CustomPath>()
    init {
        setUpDrawing()
    }
    private fun setUpDrawing(){
        mDrawPaint = Paint()
        mDrawPath = CustomPath(color, mBrushSize)
        mDrawPaint!!.color = color
        mDrawPaint!!.style= Paint.Style.STROKE
        mDrawPath!!.strokeJoin = Paint.Join.ROUND//Default join style used for connections between line and curve segments
        mDrawPath!!.strokeCap = Paint.Cap.ROUND/*StrokeCap->Default cap used for line endings
        Paint.Cap.ROUND->The stroke projects out as a semicircle, with the center at the end of the path. */
        mCanvasPaint = Paint(Paint.DITHER_FLAG)
        //mBrushSize = 20.toFloat()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCanvasBitmap = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(mCanvasBitmap!!)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(mCanvasBitmap!!,0f,0f,mCanvasPaint)
        for(path in mPaths){
            mDrawPaint!!.strokeWidth= path.brushthickness
            mDrawPaint!!.color= path.color
            canvas.drawPath(path,mDrawPaint!!)
        }

        if(!mDrawPath!!.isEmpty){

            mDrawPaint!!.strokeWidth = mDrawPath!!.brushthickness
            mDrawPaint!!.color = mDrawPath!!.color
            canvas.drawPath(mDrawPath!!,mDrawPaint!!)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchX = event?.x
        val touchy = event?.y
        when(event?.action){
            MotionEvent.ACTION_DOWN ->{
                mDrawPath!!.color = color
                mDrawPath!!.brushthickness = mBrushSize
                mDrawPath!!.reset()
                if(touchX != null){
                    if(touchy != null){
                        mDrawPath!!.moveTo(touchX,touchy)
                    }
                }
            }
            MotionEvent.ACTION_MOVE ->{
                if(touchX != null) {
                    if (touchy != null) {

                        mDrawPath!!.lineTo(touchX, touchy)
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                    mPaths.add(mDrawPath!!)
                mDrawPath = CustomPath(color,mBrushSize)
            }

            else->return false

        }
        invalidate()// if we wamt to show changes on the screen
        return true
    }
    fun setSizeforBrush(newSize : Float){
        mBrushSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
        newSize,resources.displayMetrics)
        mDrawPaint!!.strokeWidth = mBrushSize
    }
    fun setColor(newColor : String){
           color = Color.parseColor(newColor)
            mDrawPaint!!.color = color
    }

    internal inner class CustomPath(var color: Int, var brushthickness: Float) : Path() {

        lateinit var strokeCap: Paint.Cap
        lateinit var strokeJoin: Paint.Join
    }
}