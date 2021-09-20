package com.mubassyir.financialplan

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.mubassyir.financialplan.data.model.Transaction
import com.mubassyir.financialplan.databinding.ActivityTransactionFormBinding
import com.mubassyir.financialplan.model.TransactionType
import com.mubassyir.financialplan.viewmodel.TransactionViewModel


class TransactionForm : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionFormBinding
    private lateinit var adapter: ArrayAdapter<TransactionType>
    private lateinit var vm: TransactionViewModel
    private var transactionTypeCode: Int? = null


    companion object {
        const val NAME_TYPE_UPDATE = "name_type_update"
        const val CODE_TYPE_UPDATE = 21
        private const val CASH_IN = "Cash In"
        private const val CASH_OUT = "Cash Out"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionFormBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        with(supportActionBar){
            this?.setDisplayHomeAsUpEnabled(true)
            this?.setDisplayHomeAsUpEnabled(true)
        }
        setupSpinner()

        vm = ViewModelProviders.of(this)[TransactionViewModel::class.java]

        vm.getAllTransaction().observe(this, {
            Log.i("getAll data", it.toString())
        })

        intent?.let {
            if ( it.getIntExtra(NAME_TYPE_UPDATE, 0) == CODE_TYPE_UPDATE){
                actUpdate()
            }
        }

        binding.btnSave.setOnClickListener {
            val nominal = binding.etNominal.text.toString().toLong()
            val date = System.currentTimeMillis()
            val note = binding.etNote.text.toString()
            val transactionType = if (transactionTypeCode == 0)  CASH_OUT else CASH_IN
            Toast.makeText(this@TransactionForm,transactionTypeCode.toString(),Toast.LENGTH_SHORT).show()

            vm.insert(Transaction(0,date,nominal,transactionType,note))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.transaction_form_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.btn_menu_close -> {
            onBackPressed()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun setupSpinner() {
        val customObjects = getCustomObjects()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, customObjects)
        binding.spTransactionType.adapter = adapter
        binding.spTransactionType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                val selectedObject = binding.spTransactionType.selectedItem as TransactionType
                transactionTypeCode = selectedObject.id
            }
        }
    }

    private fun actUpdate() {
        TODO("Not yet implemented")
    }

    private fun actAdd() {
        TODO("Not yet implemented")
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getCustomObjects(): ArrayList<TransactionType> {
        val customObjects = ArrayList<TransactionType>()
        customObjects.apply {
            add(TransactionType(1, "Cash In"))
            add(TransactionType(2, "Cash Out"))
        }
        return customObjects
    }

}