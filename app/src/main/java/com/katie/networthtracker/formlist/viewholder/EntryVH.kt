package com.katie.networthtracker.formlist.viewholder

import android.view.View
import com.katie.networthtracker.data.Entry
import com.katie.networthtracker.data.FormData
import kotlinx.android.synthetic.main.vh_entry.view.*

class EntryVH(root: View) : FormVH(root) {

    init {
        root.group_amount.visibility = View.VISIBLE
    }

    override fun bind(data: FormData) {
        super.bind(data)

        if (data !is Entry) {
            return
        }

        itemView.amount.setText(data.amount)
    }
}

