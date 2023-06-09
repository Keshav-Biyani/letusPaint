package com.keshav.letuspaint

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get

class MainActivity : AppCompatActivity() {
    private var mColorPreview: View? = null
    private var mDefaultColor = 0
    private  var  DrawingVeiw : DrawingVeiw? = null
    private var ib_Brush : ImageButton? = null
    private var mImageButtonCurrentPaint : ImageButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         DrawingVeiw = findViewById(R.id.DrawingVeiw)
        val linearLayoutPaintColrse = findViewById<LinearLayout>(R.id.ll_colors)
        mImageButtonCurrentPaint = linearLayoutPaintColrse[0] as ImageButton
        mImageButtonCurrentPaint!!.setImageDrawable(
            ContextCompat.getDrawable(this , R.drawable.color_pressed)
        )
        ib_Brush = findViewById(R.id.ib_brush)
        DrawingVeiw?.setSizeforBrush(20.toFloat())
        ib_Brush?.setOnClickListener{
            showBrushSizeChooserDialog()
        }
    }
    private fun showBrushSizeChooserDialog(){
        val brushDialog = Dialog(this)
        brushDialog.setContentView(R.layout.dialog_brush_size)
        brushDialog.setTitle("Brush size: ")
        val smallBtn : ImageButton = brushDialog.findViewById<ImageButton>(R.id.ib_small_brush)
        smallBtn.setOnClickListener{
            DrawingVeiw?.setSizeforBrush(10.toFloat())
            brushDialog.dismiss()

        }
        val medBtn : ImageButton= brushDialog.findViewById<ImageButton>(R.id.ib_medium_brush)
        medBtn.setOnClickListener{
            DrawingVeiw?.setSizeforBrush(20.toFloat())
            brushDialog.dismiss()
        }
        val largeBtn : ImageButton = brushDialog.findViewById<ImageButton>(R.id.ib_large_brush)
        largeBtn.setOnClickListener{
            DrawingVeiw?.setSizeforBrush(30.toFloat())
            brushDialog.dismiss()
        }
        brushDialog.show()

    }
    fun paintClicked(view : View){
        if(view != mImageButtonCurrentPaint){
            val imageButton = view as ImageButton
            val colorTag = imageButton.tag.toString()
            DrawingVeiw?.setColor(colorTag)

            imageButton.setImageDrawable(
                ContextCompat.getDrawable(this , R.drawable.color_pressed)
            )
            mImageButtonCurrentPaint!!.setImageDrawable(
                ContextCompat.getDrawable(this , R.drawable.red)
            )
            mImageButtonCurrentPaint = view




        }
    }
}


