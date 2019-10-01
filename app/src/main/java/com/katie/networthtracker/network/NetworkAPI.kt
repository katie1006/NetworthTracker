package com.katie.networthtracker.network

import com.katie.networthtracker.Utils
import com.katie.networthtracker.data.Entry
import com.katie.networthtracker.data.EntryGroup

class NetworkAPI {
    fun getNetworth(assets: EntryGroup, liabilities: EntryGroup, currency: String): Int {
        return getTotalAssets(assets) - getTotalLiability(liabilities)
    }

    fun getTotalLiability(liabilities: EntryGroup): Int {
        return getTotal(liabilities)
    }

    fun getTotalAssets(assets: EntryGroup): Int {
        return getTotal(assets)
    }

    private fun getTotal(entryGroup: EntryGroup): Int {
        return entryGroup.subgroups?.sumBy { getTotal(it) } ?: 0 + (entryGroup.entries?.sumBy { it.amount } ?: 0)
    }

    fun getNetworthForm(): List<EntryGroup> {
        // here comes fake data for POC
        val data = arrayListOf<EntryGroup>()

        // assets
        val assetsEntries = arrayListOf<EntryGroup>()
        // cash and investments
        val cashEntries = arrayListOf<Entry>()
        cashEntries.add(Entry("Chequing"))
        cashEntries.add(Entry("Savings for Taxes"))
        cashEntries.add(Entry("Rainy Day Fund"))
        cashEntries.add(Entry("Savings for Fun"))
        cashEntries.add(Entry("Savings for Travel"))
        assetsEntries.add(EntryGroup("Cash and Investments", null, cashEntries))
        // long term assets
        val longTermEntries = arrayListOf<Entry>()
        longTermEntries.add(Entry("Primary Home"))
        longTermEntries.add(Entry("Second Home"))
        assetsEntries.add(EntryGroup("Long Term Assets", null, longTermEntries))
        // other
        assetsEntries.add(EntryGroup("Other", null, null))
        data.add(EntryGroup(Utils.TITLE_ASSETS, assetsEntries, null))

        // liabilities
        val liabilitiesEntries = arrayListOf<EntryGroup>()
        // short term assets
        val shortTermEntries = arrayListOf<Entry>()
        shortTermEntries.add(Entry("Credit Card 1"))
        shortTermEntries.add(Entry("Credit Card 2"))
        liabilitiesEntries.add(EntryGroup("Short Term Liabilties", null, shortTermEntries))
        // long term debt
        val debtEntries = arrayListOf<Entry>()
        debtEntries.add(Entry("Mortgage 1"))
        debtEntries.add(Entry("Mortgage 2"))
        debtEntries.add(Entry("Line of Credit"))
        debtEntries.add(Entry("Investment Loan"))
        debtEntries.add(Entry("Student Loan"))
        debtEntries.add(Entry("Car Loan"))
        liabilitiesEntries.add(EntryGroup("Long Term Debt", null, debtEntries))
        data.add(EntryGroup(Utils.TITLE_LIABILITIES, liabilitiesEntries, null))

        return data
    }


}