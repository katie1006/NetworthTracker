package com.katie.networthtracker.formlist.viewholder

import android.content.Context
import android.view.View
import kotlinx.android.synthetic.main.vh_header.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager


class HeaderVH(root: View, listener: CurrencyListener) : FormVH(root) {
    init {
        root.entry_name.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                listener.onCurrencyChanged(itemView.entry_name.text.toString())
                val imm = textView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(textView.windowToken, 0)
                true
            } else {
                false
            }
        }
    }
}

interface CurrencyListener {
    fun onCurrencyChanged(currency: String)
}