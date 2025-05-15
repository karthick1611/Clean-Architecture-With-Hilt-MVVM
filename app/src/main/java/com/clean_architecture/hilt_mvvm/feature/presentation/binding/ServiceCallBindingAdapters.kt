package com.clean_architecture.hilt_mvvm.feature.presentation.binding

import android.widget.TextView
import androidx.core.content.ContextCompat
import com.clean_architecture.hilt_mvvm.R
import androidx.databinding.BindingAdapter

class ServiceCallBindingAdapters {

    @BindingAdapter("nullableText")
    fun TextView.setNullableText(value: String?) {
        text = value ?: "--"
    }

    @BindingAdapter("statusText", "openText", requireAll = true)
    fun TextView.setStatusColor(status: String?, openText: String) {
        text = status ?: "--"
        val colorRes = if (status == openText) R.color.openColor else R.color.closeColor
        setTextColor(ContextCompat.getColor(context, colorRes))
    }

}