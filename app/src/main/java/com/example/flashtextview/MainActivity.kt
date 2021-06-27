package com.example.flashtextview

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val root = findViewById<ViewGroup>(R.id.rootView)
        val flashTextView: FlashTextView
        root.addView(FlashTextView(this).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setText("This is test;This is test")
            flashTextView = this
            setBackgroundColor(Color.BLUE)
        })

        val testBtn = findViewById<Button>(R.id.testBtn)
        testBtn.setOnClickListener {
//            flashTextView.setTextColor(Color.WHITE)
            flashTextView.setTextSize(120F)
        }

    }
}