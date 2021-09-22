package com.mubassyir.financialplan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mubassyir.financialplan.adapter.TransactionAdapter
import com.mubassyir.financialplan.databinding.ActivityMainBinding
import com.mubassyir.financialplan.viewmodel.TransactionViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var vm: TransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        vm = ViewModelProviders.of(this)[TransactionViewModel::class.java]

        setContentView(view)
        setupRv()
        setupBalance()

        binding.btnAdd.setOnClickListener {
            Intent(this, TransactionForm::class.java).also {
                startActivity(it)
            }
        }

    }

    private fun setupBalance() {
        vm.getAllCashIn().observe(this,{cashIn->
            vm.getAllCashOut().observe(this@MainActivity,{ cashOut->
                binding.tvBalance.text = cashIn.sum().minus(cashOut.sum()).toString()
            })
        })
    }

    private fun setupRv() {
        val adapter = TransactionAdapter()
        binding.pbMain.visibility = View.VISIBLE
        vm.getAllTransaction().observe(this, {
            if (it.isEmpty()) binding.layoutNoDataRecord.visibility = View.VISIBLE else binding.layoutNoDataRecord.visibility = View.INVISIBLE
            adapter.setData(it)
            adapter.notifyDataSetChanged()
            binding.pbMain.visibility = View.INVISIBLE
        })
        with(binding.rvTransaction) {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_menu_logout -> {
                val prefs = getSharedPreferences("login", MODE_PRIVATE)
                prefs.edit().remove("isUserLogin").apply().also {
                    Intent(this@MainActivity, LoginActivity::class.java).also { int ->
                        startActivity(int)
                        finish()
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}


