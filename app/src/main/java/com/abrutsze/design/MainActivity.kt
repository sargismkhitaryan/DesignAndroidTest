package com.abrutsze.design

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.LineBackgroundSpan
import android.view.View
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val text = "Charlotte Perriand’s “La maison au bord de l’eau”"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTextWithSpan(findViewById<View>(R.id.tv) as TextView, resources.getColor(android.R.color.white), text, 1.1f)
    }

    private fun setTextWithSpan(textView: TextView, backgroundColor: Int, text: String, lineSpacingMultiplier: Float) {
        class BackgroundColorSpanWithPaddingAndLineSpacing(private val paddingSize: Int, private val roundedCornerSize: Float) : LineBackgroundSpan {
            private val rect: RectF = RectF()

            override fun drawBackground(c: Canvas, p: Paint, left: Int, right: Int, top: Int, baseline: Int, bottom: Int, text: CharSequence, start: Int, end: Int, currentLineNumber: Int) {
                val textWidth = Math.round(p.measureText(text, start, end))
                val paintColor = p.color

                rect.set((left - paddingSize / 2).toFloat(), (top - paddingSize / 4).toFloat(), (left + textWidth + paddingSize / 2).toFloat(), top.toFloat() + textView.textSize + (paddingSize).toFloat()/2)
                p.color = backgroundColor
                c.drawRoundRect(rect, roundedCornerSize, roundedCornerSize, p)
                p.color = paintColor
            }
        }

        val padding = textView.paddingLeft
        val radius = padding / 2

        val builder = SpannableStringBuilder(text)
        val backgroundSpan = BackgroundColorSpanWithPaddingAndLineSpacing(padding, radius.toFloat())
        builder.setSpan(backgroundSpan, 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        textView.setShadowLayer(padding.toFloat(), 0f, 0f, 0)
        textView.setLineSpacing(0f, lineSpacingMultiplier)

        textView.setText(builder, TextView.BufferType.SPANNABLE)
    }

}
