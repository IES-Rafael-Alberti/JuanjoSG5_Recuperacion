package fileHandling

import java.io.File


/**
 * The `Reader` class provides functionality to read data from a CSV file.
 */
class Reader {
    private val matrix: MutableList<MutableList<String>> = mutableListOf()

    /**
     * Reads the contents of a CSV file located at the given [path] and returns the data as a mutable list of mutable lists.
     * Each inner list represents a row in the CSV file, and each element in the inner list represents a cell value.
     *
     * @param path The path to the CSV file.
     * @return The data from the CSV file as a mutable list of mutable lists.
     */
    fun read(path: String): MutableList<MutableList<String>> {
        File(path).bufferedReader().use { reader ->
            var line = reader.readLine()
            while (line != null) {
                val row = parseRow(line)
                matrix.add(row)
                line = reader.readLine()
            }
        }
        return matrix
    }

    /**
     * Parses a single row from a CSV file line and returns it as a mutable list of cell values.
     *
     * @param line The line from the CSV file.
     * @return The row parsed from the CSV line.
     */
    private fun parseRow(line: String): MutableList<String> {
        val row = mutableListOf<String>()
        val currentCell = StringBuilder()

        var i = 0
        while (i < line.length) {
            when (val currentChar = line[i]) {
                ',' -> {
                    addCellToRow(row, currentCell)
                    currentCell.clear()
                }
                '"' -> {
                    val quotedValue = extractQuotedValue(line, i)
                    currentCell.append(quotedValue)
                    i += quotedValue.length + 1
                }
                else -> currentCell.append(currentChar)
            }
            i++
        }

        addCellToRow(row, currentCell)
        return row
    }

    /**
     * Extracts the value within quotes from a CSV line, starting from the given [startIndex].
     *
     * @param line The line from the CSV file.
     * @param startIndex The index where the quoted value starts.
     * @return The extracted quoted value.
     */
    private fun extractQuotedValue(line: String, startIndex: Int): String {
        val endIndex = line.indexOf('"', startIndex + 1)
        return line.substring(startIndex + 1, endIndex)
    }

    /**
     * Adds a cell value to a row after trimming it and converting it to a string.
     *
     * @param row The row to which the cell value will be added.
     * @param cell The cell value.
     */
    private fun addCellToRow(row: MutableList<String>, cell: StringBuilder) {
        row.add(cell.toString().trim())
    }



}





fun main() {
    val csvFile = "src/jvmMain/kotlin/files/ud1.csv"  // Replace with the actual file path
    val reader = Reader()
    val matrix = reader.read(csvFile)


    println(matrix[19][8])
    println(matrix[8][19])
}

