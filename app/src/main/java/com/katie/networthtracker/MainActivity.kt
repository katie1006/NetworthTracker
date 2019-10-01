package com.katie.networthtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.katie.networthtracker.formlist.FormAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val formAdapter = FormAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.setTitle(R.string.page_title)
        setContentView(R.layout.activity_main)

        // set up recyclerview
        form_list.layoutManager = LinearLayoutManager(this)
        form_list.adapter = formAdapter

        formAdapter.updateData()
    }
}
