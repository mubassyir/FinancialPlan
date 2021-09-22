package com.mubassyir.financialplan.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mubassyir.financialplan.data.model.Transaction

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(transaction: Transaction)

    @Update
    fun update(transaction: Transaction)

    @Delete
    fun delete(transaction: Transaction)

    @Query("SELECT * from `Transaction` ORDER BY id ASC")
    fun getrAllTransaction(): LiveData<List<Transaction>>

    @Query ("SELECT amount FROM 'Transaction' WHERE transaction_type = 'Cash In' ")
    fun getAllCashIn():LiveData<List<Long>>

    @Query ("SELECT amount FROM 'Transaction' WHERE transaction_type = 'Cash Out' ")
    fun getAllCashOut():LiveData<List<Long>>
}