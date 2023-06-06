package fileHandling

import railway.Result
import railway.Results

object DTOcsv : Reader() {
    val resultAprend = mutableListOf<String>()
    val critEvalu = mutableListOf<String>()

    init {
        read("src/jvmMain/kotlin/files/ud1.csv")
        resultAprendFiller()
        critEvaluFiller()
    }

    private fun resultAprendFiller() {
        val regex = Regex("^UD1\\.[a-i]\$")
        matrix.forEach { row ->
            if (regex.matches(row[1])) {
                resultAprend.add(row[1])
            }

        }
    }

    private fun critEvaluFiller() {
        val regex = Regex("^(?!UD1\\.[a-i])[a-z](,[a-z])*$")
        matrix.forEach { row ->
            if (regex.matches(row[1])) {
                println(row[1])
            }
        }
    }


    /**
     * Finds the position of the first element in the rows that contains a percentage sign (%).
     *
     * @param rows A list of rows, where each row is represented as a list of strings.
     * @return The index of the first element with a percentage sign. If no element is found, -1 is returned.
     */
    fun getIndexOfPercentage(rows: MutableList<MutableList<String>>): Result<Int, Results> {
        rows.forEach { row ->
            row.forEach { element ->
                if (element.contains('%')) {
                    return Result(row.indexOf(element), Results.SUCCESSFUL)
                }
            }
        }
        return Result(-1, Results.FAILURE)
    }

    /**
     * Retrieves the rows from the matrix that contain any of the specified elements to calculate.
     *
     * @param matrix The matrix represented as a list of rows, where each row is represented as a list of strings.
     * @param raToCalculate The elements to search for within the rows.
     * @return A list of rows that match the specified elements.
     * If no matching rows are found, an empty list is returned.
     */
    fun obtainRows(
        matrix: MutableList<MutableList<String>>,
        raToCalculate: MutableList<String>
    ): Result<MutableList<MutableList<String>>, Results> {
        val rowOfResult = mutableListOf<MutableList<String>>()
        matrix.forEach { row ->
            for (crit: String in raToCalculate) {
                if (row.contains(crit) && !rowOfResult.contains(row)) {
                    rowOfResult.add(row)
                }
            }
        }
        return if (rowOfResult.isNotEmpty()) {
            Result(rowOfResult, Results.SUCCESSFUL)
        } else {
            Result(rowOfResult, Results.FAILURE)
        }
    }

    /**
     * Deletes elements in each row of a list before a given index.
     *
     * @param list The list of rows.
     * @param index The index before which elements should be deleted.
     * @return The modified list with elements deleted before the index.
     */
    fun deleteBeforeIndex(list: MutableList<MutableList<String>>, index: Int): Result<MutableList<MutableList<String>>,Results> {
        val result = list.map { row ->
            row.drop(index).toMutableList()
        }.toMutableList()

        return if (result.isNotEmpty()) {
            Result(result, Results.SUCCESSFUL)
        } else {
            Result(result, Results.FAILURE)
        }
    }

}

fun main() {
    DTOcsv

}