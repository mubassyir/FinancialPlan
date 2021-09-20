package com.mubassyir.financialplan.model

data class TransactionType(
        var id: Int = 0,
        var name: String = ""
){

    override fun toString(): String {
        return name
    }

}