package com.mubassyir.financialplan.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.mubassyir.financialplan.data.TransactionDao
import com.mubassyir.financialplan.data.TransactionRoomDatabase
import com.mubassyir.financialplan.data.model.Transaction
import com.mubassyir.financialplan.utils.subscribeOnBackground

class TransactionRepository (ctx:Application){
    private var transactionDao : TransactionDao
    private val allTransaction : LiveData<List<Transaction>>

    private val database = TransactionRoomDatabase.getInstance(ctx)

    init {
        transactionDao = database.transactionDao()
        allTransaction = transactionDao.getrAllTransaction()
    }

    fun getAllTransaction(): LiveData<List<Transaction>> {
        return allTransaction
    }
    fun insert(transaction: Transaction){
        subscribeOnBackground {
            transactionDao.insert(transaction)
        }
    }

    fun update(transaction:Transaction){
        subscribeOnBackground {
            transactionDao.update(transaction)
        }
    }

    fun delete(transaction: Transaction){
        subscribeOnBackground {
            transactionDao.delete(transaction)
        }
    }
}