package com.example.androidsamples.activity

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.ReplacementSpan
import android.text.style.StyleSpan
import android.text.style.URLSpan
import android.text.style.UnderlineSpan
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.clearSpans
import androidx.core.text.toSpannable
import com.example.androidsamples.R

private val list =
    listOf(
        "文字中",
        "文字小",
        "文字大",
        "色変更",
        "太字",
        "下線",
        "取り消し線",
        "斜体",
        "色変更",
        "ハイパーリンク",
        "太字かつ斜体"
    )

private val exclude = listOf("太字かつ斜体", "斜体")


class RichEditTextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rich_edit_text)
        findViewById<EditText>(R.id.rich_edit_text).apply {
            val builder =
                SpannableStringBuilder("文字大\n文字中\n文字小\n色変更\n太字\n下線\n取り消し線\n斜体\nハイパーリンク\n太字かつ斜体")
            for (item in list) {
                val first = this.text.indexOf(item)
                val last = first + item.length
                setSpan(builder, item, first, last)
            }
            this.text = builder
            this.movementMethod = LinkMovementMethod.getInstance()
        }
        findViewById<EditText>(R.id.rich_edit_text_all).apply {
            val builder = SpannableStringBuilder("全部")
            for (item in list) {
                if (exclude.contains(item)) continue
                setSpan(builder, item, 0, this.text.length)
            }
            this.text = builder
            this.movementMethod = LinkMovementMethod.getInstance()
        }
        findViewById<Button>(R.id.clearButton).setOnClickListener {
            val allSpan = findViewById<TextView>(R.id.rich_edit_text_all).text.toSpannable()
            allSpan.clearSpans()
            findViewById<TextView>(R.id.rich_edit_text).text = allSpan
            val textSpan = findViewById<TextView>(R.id.rich_edit_text).text.toSpannable()
            textSpan.clearSpans()
            findViewById<TextView>(R.id.rich_edit_text_all).text = textSpan
        }
        findViewById<EditText>(R.id.checkbox).apply {
            val builder = SpannableStringBuilder("- [x]あいうえお")
            builder.setSpan(CheckboxReplacementSpan(), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            this.text = builder
        }

        findViewById<EditText>(R.id.bold).apply {
            val builder = SpannableStringBuilder("**あいうえお**")
            builder.setSpan(GoneSpan(), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            builder.setSpan(StyleSpan(Typeface.BOLD), 2, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            builder.setSpan(
                GoneSpan(),
                this.text.length - 2,
                this.text.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            this.text = builder
        }
    }

    private fun setSpan(builder: SpannableStringBuilder, item: String, start: Int, end: Int) {
        val span = when (item) {
            "文字大" -> RelativeSizeSpan(2f)
            "文字中" -> RelativeSizeSpan(1.5f)
            "文字小" -> RelativeSizeSpan(1f)
            "色変更" -> ForegroundColorSpan(Color.RED)
            "青変更" -> ForegroundColorSpan(Color.BLUE)
            "太字" -> StyleSpan(Typeface.BOLD)
            "下線" -> UnderlineSpan()
            "取り消し線" -> android.text.style.StrikethroughSpan()
            "斜体" -> StyleSpan(Typeface.ITALIC)
            "ハイパーリンク" -> URLSpan("https://stackoverflow.com/questions/18621691/custom-sized-italic-font-using-spannable")
            "太字かつ斜体" -> StyleSpan(Typeface.BOLD_ITALIC)
            else -> throw IllegalStateException(item)
        }
        if (start < 0) return
        builder.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    private inner class CheckboxReplacementSpan
    constructor() : ReplacementSpan() {
        override fun getSize(
            paint: Paint,
            text: CharSequence?,
            start: Int,
            end: Int,
            fm: Paint.FontMetricsInt?
        ): Int {
            return paint.measureText("✅", 0, 1).toInt()
        }

        override fun draw(
            canvas: Canvas,
            text: CharSequence?,
            start: Int,
            end: Int,
            x: Float,
            top: Int,
            y: Int,
            bottom: Int,
            paint: Paint
        ) {
            canvas.drawText("✅", 0, 1, x, y.toFloat(), paint);
        }
    }

    class GoneSpan : ReplacementSpan() {
        override fun getSize(
            paint: Paint,
            text: CharSequence?,
            start: Int,
            end: Int,
            fm: Paint.FontMetricsInt?
        ): Int = 0

        override fun draw(
            canvas: Canvas,
            text: CharSequence?,
            start: Int,
            end: Int,
            x: Float,
            top: Int,
            y: Int,
            bottom: Int,
            paint: Paint
        ) {
        }
    }
}