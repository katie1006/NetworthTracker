package com.katie.networthtracker.formlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.katie.networthtracker.R
import com.katie.networthtracker.Utils.TITLE_ASSETS
import com.katie.networthtracker.Utils.TITLE_LIABILITIES
import com.katie.networthtracker.data.Entry
import com.katie.networthtracker.data.EntryGroupPlaceholder
import com.katie.networthtracker.data.FormDataWrapper
import com.katie.networthtracker.formlist.viewholder.*

class FormAdapter : RecyclerView.Adapter<FormVH>(), EntryAmountListener, CurrencyListener {

    private val dataset = arrayListOf<FormDataWrapper>()
    private var formPresenter: FormContract.Presenter? = null

    fun subscribe(presenter: FormContract.Presenter) {
        formPresenter = presenter
    }

    fun unsubscribe() {
        formPresenter = null
        dataset.clear()
        notifyDataSetChanged()
    }

    fun update(data: List<FormDataWrapper>) {
        dataset.clear()
        dataset.addAll(data)
        notifyDataSetChanged()
    }

    fun updateNetworth(amount: Int) {
        updateCalcView(CalcView.NET_WORTH, amount)
    }

    fun updateTotalAssets(amount: Int) {
        updateCalcView(CalcView.TOTAL_ASSETS, amount)
    }

    fun updateTotalLiability(amount: Int) {
        updateCalcView(CalcView.TOTAL_LIABILITIES, amount)
    }

    private fun updateCalcView(calcView: CalcView, amount: Int) {
        val position = dataset.indexOfFirst {
            it.path.contentEquals(calcView.path) && it.positionInType == calcView.ordinal
        }

        if (position < 0 || position >= dataset.size) {
            return
        }

        val candidate = dataset[position].data as? Entry
        if (candidate?.amount != amount) {
            candidate?.amount = amount
            notifyItemChanged(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormVH {
        // todo: this might throw IndexOutOfBoundsException. fail in a more graceful way
        val candidateType = ViewType.values()[viewType]

        val root = LayoutInflater.from(parent.context).inflate(candidateType.layoutId, parent, false)
        return when (candidateType) {
            ViewType.VIEW_TYPE_ENTRY -> {
                val result = EntryVH(root, this)
                result.setNotEditable()
                result
            }
            ViewType.VIWE_TYPE_ENTRY_EDITABLE -> EntryVH(root, this)
            ViewType.VIEW_TYPE_HEADER -> HeaderVH(root, this)
            else -> FormVH(root)
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
            item.data is Entry -> {
                if (item.path.contentEquals(CalcView.NET_WORTH.path)) {
                    ViewType.VIEW_TYPE_ENTRY.ordinal
                } else {
                    ViewType.VIWE_TYPE_ENTRY_EDITABLE.ordinal
                }
            }
            else -> -1
        }
    }

    override fun onAmountChanged(pos: Int, amount: Int) {
        formPresenter?.onEntryAmountChanged(dataset[pos].path, amount)
    }

    override fun onCurrencyChanged(currency: String) {
        formPresenter?.onCurrencyChanged(currency)
    }

    private enum class ViewType(val layoutId: Int) {
        VIEW_TYPE_HEADER(R.layout.vh_header),
        VIEW_TYPE_ENTRY_GROUP(R.layout.vh_entry),
        VIWE_TYPE_ENTRY_EDITABLE(R.layout.vh_entry),
        VIEW_TYPE_ENTRY(R.layout.vh_entry)
    }
}

enum class CalcView(val title: String) {
    NET_WORTH("Networth"),
    TOTAL_ASSETS("Total $TITLE_ASSETS"),
    TOTAL_LIABILITIES("Total $TITLE_LIABILITIES")
    ;

    val path = arrayOf("total_amount")

    fun getWrapper(): FormDataWrapper {
        return FormDataWrapper(Entry(title), path, ordinal)
    }
}
