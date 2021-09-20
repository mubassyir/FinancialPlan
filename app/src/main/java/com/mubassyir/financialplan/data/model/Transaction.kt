package com.mubassyir.financialplan.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "transaction")
@Parcelize
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id:Int,

    @ColumnInfo(name="date")
    val date:Long,

    @ColumnInfo(name="amount")
    val amount: Long,

    @ColumnInfo(name="transaction_type")
    val transactionType : String,

    @ColumnInfo(name = "note")
    val note:String,

):Parcelable