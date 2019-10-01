package com.katie.networthtracker.formlist.viewholder

import android.content.Context
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import com.katie.networthtracker.data.Entry
import com.katie.networthtracker.data.FormData
import kotlinx.android.synthetic.main.vh_entry.view.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class EntryVH(root: View, listener: EntryAmountListener) : FormVH(root) {
    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())

    init {
        root.group_amount.visibility = View.VISIBLE
        root.amount.doAfterTextChanged { amountText ->
            amountText?.toString()?.toIntOrNull()?.let {
                listener.onAmountChanged(adapterPosition, it)
            }
        }
        root.amount.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                root.entry_name.requestFocus()
                true
            } else {
                false
            }
        }

        currencyFormat.currency = Currency.getInstance("CAD")
        if (currencyFormat is DecimalFormat) {
            val decimalFormatSymbols = currencyFormat.decimalFormatSymbols
            decimalFormatSymbols.currencySymbol = ""
            currencyFormat.decimalFormatSymbols = decimalFormatSymbols
        }

        root.amount.setOnFocusChangeListener { view, hasFocus ->
            Log.i("katiee", "focus changed to $hasFocus")
            if (hasFocus) {
                // format amount to integers
                (view as? EditText)?.text?.toString()?.let {
                    itemView.amount.setText(currencyFormat.parse(it)?.toString())
                }
            } else {
                // dimiss keyboard
                val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)

                // format amount to string
                (view as? EditText)?.text?.toString()?.toIntOrNull()?.let {
                    itemView.amount.setText(currencyFormat.format(it))
                }
            }
        }
    }

    override fun bind(data: FormData) {
        super.bind(data)

        if (data !is Entry) {
            return
        }

        itemView.amount.setText(data.amount.toString())
    }

    fun setNotEditable() {
        Log.i("katiee", "tried")
        itemView.amount.isEnabled = false
        itemView.amount.inputType = InputType.TYPE_NULL
    }
}

interface EntryAmountListener {
    fun onAmountChanged(pos: Int, amount: Int)
}
