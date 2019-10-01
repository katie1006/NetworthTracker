package com.katie.networthtracker.formlist.viewholder

import android.text.InputType
import android.util.Log
import android.view.View
import androidx.core.widget.doAfterTextChanged
import com.katie.networthtracker.data.Entry
import com.katie.networthtracker.data.FormData
import kotlinx.android.synthetic.main.vh_entry.view.*

class EntryVH(root: View, listener: EntryAmountListener) : FormVH(root) {

    init {
        root.group_amount.visibility = View.VISIBLE
        root.amount.doAfterTextChanged {
            try {
                listener.onAmountChanged(adapterPosition, it.toString().toInt())
            } catch (e: Exception) {
                // ignore
                e.printStackTrace()
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
