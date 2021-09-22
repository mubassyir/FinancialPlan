package com.mubassyir.financialplan

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.mubassyir.financialplan.data.model.Transaction
import com.mubassyir.financialplan.databinding.ActivityTransactionFormBinding
import com.mubassyir.financialplan.model.TransactionType
import com.mubassyir.financialplan.viewmodel.TransactionViewModel


class TransactionForm : AppCompatActivity() {

    private lateinit var mode : Mode

    private lateinit var binding: ActivityTransactionFormBinding
    private lateinit var adapter: ArrayAdapter<TransactionType>
    private lateinit var vm: TransactionViewModel

    private var nominal:String=""
    private var date:Long=0
    private var note:String=""
    private var transactionType:String=""
    private var transactionTypeCode: Int? = null

    //experimental
    var id:Int = 0
    //experimental


    companion object {
        const val PAYLOAD = "payload"
        const val NAME_TYPE_UPDATE = "name_type_update"
        const val CODE_TYPE_UPDATE = 21
        private const val FIELD_REQUIRED = "Field Required"
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
        setupMode()

        vm = ViewModelProviders.of(this)[TransactionViewModel::class.java]

        binding.btnSave.setOnClickListener {
            updateOrAddTransaction()
        }
    }

    private fun updateOrAddTransaction() {
        nominal = binding.etNominal.text.toString().trim()
        date = System.currentTimeMillis()
        note = binding.etNote.text.toString().trim()
        transactionType = if (transactionTypeCode == 1) CASH_IN else CASH_OUT
        if(nominal.isEmpty()){
            with(binding.etNominal){
                error = FIELD_REQUIRED
                requestFocus()
            }
        }
        if (note.isEmpty()){
            with(binding.etNote){
                error = FIELD_REQUIRED
                requestFocus()
            }
        }
        when(mode){
            Mode.AddTransaction -> vm.insert(Transaction(0,date,nominal.toLong(),transactionType,note))
            Mode.UpdateTransaction -> vm.update(Transaction(id,date,nominal.toLong(),transactionType,note))
        }
        onBackPressed()
    }

    private fun setupMode() {
        intent?.let {
            if ( it.getIntExtra(NAME_TYPE_UPDATE, 0) == CODE_TYPE_UPDATE){
                mode = Mode.UpdateTransaction
            }
            else{
                mode = Mode.AddTransaction
            }
        }

        when(mode) {
            Mode.AddTransaction -> title = "Add Transaction"
            Mode.UpdateTransaction -> {
                title = "Update Transaction"
                intent.getParcelableExtra<Transaction>(PAYLOAD).also {
                    binding.etNominal.setText(it?.amount.toString())
                    binding.etNote.setText(it?.note)
                    id = it!!.id
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.transaction_form_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.btn_menu_close -> {
            if (mode == Mode.UpdateTransaction){
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Warning")
                builder.setMessage("Are you sure to delete?")

                builder.setPositiveButton("Yes") { dialog, which ->
                    vm.delete(Transaction(id,date,nominal.toLong(),transactionType,note))
                    onBackPressed()
                }
                builder.setNegativeButton("No"){_,_  ->}
                builder.show()
            } else{
                onBackPressed()
            }
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

    private sealed class Mode {
        object AddTransaction : Mode()
        object UpdateTransaction : Mode()
    }
}