package com.katie.networthtracker.formlist

import com.katie.networthtracker.data.CalcResult
import com.katie.networthtracker.data.EntryGroup
import com.katie.networthtracker.data.FormDataWrapper

interface FormContract {

    interface View {
        fun showErrorDialog(error: String?)
        fun updateForm(data: List<FormDataWrapper>)
        fun updateNetworth(amount: Int)
        fun updateTotalAssets(amount: Int)
        fun updateTotalLiability(amount: Int)
    }

    interface Presenter {
        fun subscribe(view: FormContract.View)
        fun unsubscribe()
        fun onEntryAmountChanged(path: Array<String>, amount: Int)
        fun onCurrencyChanged(currency: String)
    }

    interface Repo {
        fun calculate(
            input: List<EntryGroup>,
            currency: String,
            callback: FormCallback<CalcResult>
        )
        fun getForm(callback: FormContract.FormCallback<List<EntryGroup>>)
        fun cancel()
    }

    interface FormCallback<in T> {
        fun onResult(result: T)
        fun onFail(error: Throwable?)
    }
}