package com.challenge.whatswrong.core

import android.widget.TextView
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun TextView.setPrecomputedText(text: CharSequence) {
    val params = TextViewCompat.getTextMetricsParams(this)
    val precomputedText = withContext(Dispatchers.Default) {
        PrecomputedTextCompat.create(text, params)
    }
    TextViewCompat.setPrecomputedText(this, precomputedText)
}
