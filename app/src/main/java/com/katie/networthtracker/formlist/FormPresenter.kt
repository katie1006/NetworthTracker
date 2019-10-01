package com.katie.networthtracker.formlist

import com.katie.networthtracker.data.CalcResult
import com.katie.networthtracker.data.EntryGroup

class FormPresenter: FormContract.Presenter {
    private val repo: FormContract.Repo = FormRepo()
    private val dataManager = FormDataManager()
    private var view: FormContract.View? = null

    override fun subscribe(view: FormContract.View) {
        this.view = view
        repo.getForm(object : FormContract.FormCallback<List<EntryGroup>> {
            override fun onResult(result: List<EntryGroup>) {
                dataManager.update(result)
                this@FormPresenter.view?.updateForm(dataManager.getDataForUI())
            }

            override fun onFail(error: Throwable?) {
                this@FormPresenter.view?.showErrorDialog(error?.message)
            }
        })
    }

    override fun unsubscribe() {
        view = null
        repo.cancel()
        dataManager.clear()
    }

    override fun onEntryAmountChanged(path: Array<String>, amount: Int) {
        // first update local data
        dataManager.updateEntry(path, amount)
        // then send request
        repo.calculate(dataManager.getDataForCalcRequest(), dataManager.currency, object : FormContract.FormCallback<CalcResult> {
            override fun onFail(error: Throwable?) {
                this@FormPresenter.view?.showErrorDialog(error?.message)
            }

            override fun onResult(result: CalcResult) {
                this@FormPresenter.view?.updateNetworth(result.net)
                this@FormPresenter.view?.updateTotalAssets(result.totalAssets)
                this@FormPresenter.view?.updateTotalLiability(result.totalLiabilities)
            }
        })
    }

    override fun onCurrencyChanged(currency: String) {
        dataManager.currency = currency
    }
}