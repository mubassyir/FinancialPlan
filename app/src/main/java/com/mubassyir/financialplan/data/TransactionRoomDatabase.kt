package com.mubassyir.financialplan.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mubassyir.financialplan.data.model.Transaction

@Database(
    entities = [Transaction::class],
    version = 1
)
abstract class TransactionRoomDatabase : RoomDatabase() {

    abstract fun transactionDao():TransactionDao

    companion object{
        private var instance: TransactionRoomDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): TransactionRoomDatabase {
            if(instance == null)
                instance = Room.databaseBuilder(ctx.applicationContext, TransactionRoomDatabase::class.java,
                    "financial_db")
                    .fallbackToDestructiveMigration()
                    .build()

            return instance!!

        }
    }

}