package com.katie.networthtracker.formlist

import com.katie.networthtracker.Utils
import com.katie.networthtracker.Utils.TITLE_ASSETS
import com.katie.networthtracker.Utils.TITLE_LIABILITIES
import com.katie.networthtracker.data.*

class FormDataManager {
    private var formData = EntryGroup("")
    var currency = "CAD"

    fun update(data: List<EntryGroup>) {
        formData = EntryGroup("", data)
    }

    fun updateEntry(path: Array<String>, amount: Int) {
        findEntry(formData, path)?.amount = amount
    }

    private fun findEntry(group: EntryGroup?, path: Array<String>): Entry? {
        if (group == null || path.isEmpty()) {
            return null
        }

        if (path.size == 1) {
            // must be an entry
            return group.entries?.find { it.name == path[0] }
        } else {
            return findEntry(
                group.subgroups?.find { it.name == path[0] },
                path.sliceArray(1 until path.size)
            )
        }
    }

    fun getDataForUI(): List<FormDataWrapper> {
        val result = arrayListOf<FormDataWrapper>()
        // first header
        result.add(FormDataWrapper(Entry(currency), emptyArray()))
        // then networth
        result.add(CalcView.NET_WORTH.getWrapper())
        // now assets
        formData.subgroups?.find { it.name == TITLE_ASSETS }?.let {
            addWrapper(result, it, emptyArray())
        }
        // now asset total
        result.add(CalcView.TOTAL_ASSETS.getWrapper())
        // then liabilities
        formData.subgroups?.find { it.name == TITLE_LIABILITIES }?.let {
            addWrapper(result, it, emptyArray())
        }
        // now liability total
        result.add(CalcView.TOTAL_LIABILITIES.getWrapper())

        // done!
        return result
    }

    private fun addWrapper(dataset: ArrayList<FormDataWrapper>, entry: Entry, parentPath: Array<String>) {
        dataset.add(FormDataWrapper(entry, parentPath.plus(entry.name)))
    }

    private fun addWrapper(dataset: ArrayList<FormDataWrapper>, entryGroup: EntryGroup, parentPath: Array<String>) {
        val currentPath = parentPath.plus(entryGroup.name)
        // first add self
        dataset.add(FormDataWrapper(EntryGroupPlaceholder(entryGroup.name), currentPath))

        // then add its entries, if any
        entryGroup.entries?.forEach {
            addWrapper(dataset, it, currentPath)
        }

        // then add all subgroups, if any
        entryGroup.subgroups?.forEach {
            addWrapper(dataset, it, currentPath)
        }
    }

    fun getDataForCalcRequest(): List<EntryGroup> {
        return formData.subgroups ?: emptyList()
    }

    fun clear() {
        formData = EntryGroup("")
    }
}