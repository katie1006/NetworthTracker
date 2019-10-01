package com.katie.networthtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.katie.networthtracker.data.FormDataWrapper
import com.katie.networthtracker.formlist.FormAdapter
import com.katie.networthtracker.formlist.FormContract
import com.katie.networthtracker.formlist.FormPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FormContract.View {

    private val formAdapter = FormAdapter()
    private val formPresenter = FormPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.setTitle(R.string.page_title)
        setContentView(R.layout.activity_main)

        // set up recyclerview
        formAdapter.subscribe(formPresenter)
        form_list.layoutManager = LinearLayoutManager(this)
        form_list.adapter = formAdapter

        formPresenter.subscribe(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        formPresenter.unsubscribe()
    }

    override fun showErrorDialog(error: String?) {
        AlertDialog.Builder(this)
            .setTitle(R.string.error)
            .setMessage(R.string.error_msg)
            .setNeutralButton(R.string.ok, null)
            .show()
    }

    override fun updateForm(data: List<FormDataWrapper>) {
        formAdapter.update(data)
    }

    override fun updateNetworth(amount: Int) {
        formAdapter.updateNetworth(amount)
    }

    override fun updateTotalAssets(amount: Int) {
        formAdapter.updateTotalAssets(amount)
    }

    override fun updateTotalLiability(amount: Int) {
        formAdapter.updateTotalLiability(amount)
    }
}
