package com.katie.networthtracker.formlist.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.katie.networthtracker.R
import com.katie.networthtracker.data.FormData

open class FormVH(root: View) : RecyclerView.ViewHolder(root) {
    private val name: TextView = root.findViewById(R.id.entry_name)

    open fun bind(data: FormData) {
        name.text = data.name
    }
}

