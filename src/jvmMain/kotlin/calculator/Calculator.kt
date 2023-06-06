package calculator

import fileHandling.DTOcsv

import log.Log
import railway.Result
import railway.Results


object Calculator {
    fun calculateRa(matrix: MutableList<MutableList<String>>, raToCalculate : MutableList<String>){
        val rows = DTOcsv.obtainRows(matrix, raToCalculate)
        if (rows.result == Results.FAILURE)   Log.warning("Calculator.calculateRa -> ${rows.result}")
        val index = DTOcsv.getIndexOfPercentage(rows.obj)
    }




}

fun main(){
    val lines = arrayOf(
        "a,b,c,f",
        "d,e,g,h,i",
        "d,e,g,h,i"
    ).toMutableList()
    Calculator.calculateRa(DTOcsv.matrix,lines)
}