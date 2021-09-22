package com.mubassyir.financialplan.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.mubassyir.financialplan.R
import com.mubassyir.financialplan.TransactionForm
import com.mubassyir.financialplan.TransactionForm.Companion.CODE_TYPE_UPDATE
import com.mubassyir.financialplan.TransactionForm.Companion.NAME_TYPE_UPDATE
import com.mubassyir.financialplan.TransactionForm.Companion.PAYLOAD
import com.mubassyir.financialplan.data.model.Transaction
import com.mubassyir.financialplan.databinding.RvMainBinding
import java.text.SimpleDateFormat

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

   private var data = ArrayList<Transaction>()

    fun setData (i:List<Transaction>?){
        i?.let {
            data.clear()
            data.addAll(i)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = RvMainBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
       val ts = data[position]
        holder.bind(ts)
    }

    override fun getItemCount(): Int {
        return data.size
    }
    class TransactionViewHolder(private val binding: RvMainBinding):RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SimpleDateFormat")
        fun bind(transaction:Transaction){
            with(binding){
                val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
                tvTimestamp.text = format.format(transaction.date)
                tvTransType.text = transaction.transactionType
                etNominal.text = transaction.amount.toString()
                etNote.text = transaction.note

                itemView.setOnClickListener {
                    Intent(root.context,TransactionForm::class.java).also {
                        it.putExtra(NAME_TYPE_UPDATE,21)
                        it.putExtra(PAYLOAD,transaction)
                        root.context.startActivity(it)
                    }
                }
            }
        }
    }

}