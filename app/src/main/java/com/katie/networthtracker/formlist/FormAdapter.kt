package com.katie.networthtracker.formlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.katie.networthtracker.R
import com.katie.networthtracker.Utils.PATH_TOTAL_AMOUNT
import com.katie.networthtracker.Utils.TITLE_ASSETS
import com.katie.networthtracker.Utils.TITLE_LIABILITIES
import com.katie.networthtracker.data.Entry
import com.katie.networthtracker.data.EntryGroupPlaceholder
import com.katie.networthtracker.data.FormData
import com.katie.networthtracker.data.FormDataWrapper
import com.katie.networthtracker.formlist.viewholder.EntryVH
import com.katie.networthtracker.formlist.viewholder.FormVH

class FormAdapter : RecyclerView.Adapter<FormVH>() {

    private val dataset = arrayListOf<FormDataWrapper>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormVH {
        // todo: this might throw IndexOutOfBoundsException. fail in a more graceful way
        val candidateType = ViewType.values()[viewType]

        val root = LayoutInflater.from(parent.context).inflate(candidateType.layoutId, parent, false)
        return if (candidateType == ViewType.VIEW_TYPE_ENTRY) {
            EntryVH(root)
        } else {
            FormVH(root)
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: FormVH, position: Int) {
        holder.bind(dataset[position].data)
    }

    override fun getItemViewType(position: Int): Int {
        val item = dataset[position]

        return when {
            position == 0 -> ViewType.VIEW_TYPE_HEADER.ordinal
            item.data is EntryGroupPlaceholder -> ViewType.VIEW_TYPE_ENTRY_GROUP.ordinal
            item.data is Entry -> ViewType.VIEW_TYPE_ENTRY.ordinal
            else -> -1
        }
    }

    fun updateData() {
        // todo fake data
        dataset.add(FormDataWrapper(Entry("CAD"), emptyArray()))
        dataset.add(FormDataWrapper(Entry("Networth"), arrayOf(PATH_TOTAL_AMOUNT), 0))
        dataset.add(FormDataWrapper(EntryGroupPlaceholder(TITLE_ASSETS), arrayOf(TITLE_ASSETS)))
        dataset.add(FormDataWrapper(EntryGroupPlaceholder("Cash and Investments"), arrayOf(TITLE_ASSETS, "Cash and Investments")))
        dataset.add(FormDataWrapper(Entry("Chequing"), arrayOf(TITLE_ASSETS, "Cash and Investments", "Chequing")))
        dataset.add(FormDataWrapper(Entry("Savings for Taxes"), arrayOf(TITLE_ASSETS, "Cash and Investments", "Savings for Taxes")))
        dataset.add(FormDataWrapper(EntryGroupPlaceholder("Long Term Assets"), arrayOf(TITLE_ASSETS, "Long Term Assets")))
        dataset.add(FormDataWrapper(Entry("Primary Home"), arrayOf(TITLE_ASSETS, "Long Term Assets", "Primary Home")))
        dataset.add(FormDataWrapper(EntryGroupPlaceholder("Other"), arrayOf(TITLE_ASSETS, "Other")))
        dataset.add(FormDataWrapper(Entry("Total Assets"), arrayOf(PATH_TOTAL_AMOUNT), 1))
        dataset.add(FormDataWrapper(EntryGroupPlaceholder(TITLE_LIABILITIES), arrayOf(TITLE_LIABILITIES)))
        dataset.add(FormDataWrapper(EntryGroupPlaceholder("Short Term Liabilties"), arrayOf(TITLE_LIABILITIES, "Short Term Liabilties")))
        dataset.add(FormDataWrapper(Entry("Credit Card 2"), arrayOf(TITLE_LIABILITIES, "Short Term Liabilties", "Credit Card 2")))
        dataset.add(FormDataWrapper(Entry("Total Liabilties"), arrayOf(PATH_TOTAL_AMOUNT), 2))

        notifyDataSetChanged()
    }

    private enum class ViewType(val layoutId: Int) {
        VIEW_TYPE_HEADER(R.layout.vh_header),
        VIEW_TYPE_ENTRY_GROUP(R.layout.vh_entry),
        VIEW_TYPE_ENTRY(R.layout.vh_entry)
    }
}
