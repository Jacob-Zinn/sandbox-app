package com.example.sandbox.adapters

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import timber.log.Timber

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("isRefreshing")
fun bindIsRefreshing(view: SwipeRefreshLayout, isRefreshing: Boolean) {
    view.isRefreshing = isRefreshing
}

@BindingAdapter("isRefreshingIntegerDefined")
fun bindIsRefreshingIntegerDefined(view: SwipeRefreshLayout, value: Int) {
    Timber.d("IS this working zzz $value")
    view.isRefreshing = (value == 0)
}