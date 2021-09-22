package com.mubassyir.financialplan.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mubassyir.financialplan.repository.TransactionRepository
import com.mubassyir.financialplan.data.model.Transaction

class TransactionViewModel(app:Application) :AndroidViewModel(app){

    private val repository = TransactionRepository(app)
    private val getAllTransaction = repository.getAllTransaction()
    private val getAllCashIn = repository.getAllCashIn()
    private val getAllCashOut = repository.getAllCashOut()

    fun getAllTransaction(): LiveData<List<Transaction>> {
        return getAllTransaction
    }

    fun insert(transaction:Transaction){
        repository.insert(transaction)
    }

    fun update(transaction: Transaction){
        repository.update(transaction)
    }

    fun delete(transaction: Transaction){
        repository.delete(transaction)
    }

    fun getAllCashIn(): LiveData<List<Long>> {
        return getAllCashIn
    }

    fun getAllCashOut(): LiveData<List<Long>> {
        return getAllCashOut
    }

}