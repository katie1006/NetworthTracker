package com.katie.networthtracker.formlist

import com.katie.networthtracker.Utils
import com.katie.networthtracker.data.CalcResult
import com.katie.networthtracker.data.EntryGroup
import com.katie.networthtracker.network.NetworthAPI

class FormRepo : FormContract.Repo {
    private val api = NetworthAPI()

    override fun calculate(
        input: List<EntryGroup>,
        currency: String,
        callback: FormContract.FormCallback<CalcResult>
    ) {
        val assets = input.find { it.name == Utils.TITLE_ASSETS }
        val liabilities = input.find { it.name == Utils.TITLE_LIABILITIES }

        if (assets == null || liabilities == null) {
            callback.onFail(null)
            return
        }

        // todo: currency needs to come from input
        callback.onResult(api.calculate(assets, liabilities, currency))
    }

    override fun getForm(callback: FormContract.FormCallback<List<EntryGroup>>) {
        callback.onResult(api.getNetworthForm())
    }

    override fun cancel() {

    }
}