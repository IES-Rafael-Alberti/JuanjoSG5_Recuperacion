
import java.io.File

/**
 * A class for reading data from a file into a matrix.
 */
class Reader {
    private val matrix: MutableList<MutableList<String>> = mutableListOf()

    /**
     * Reads data from a file and returns it as a matrix.
     *
     * @return the matrix containing the data from the file
     */
    fun read(path: String): MutableList<MutableList<String>> {
        File(path).bufferedReader().use { reader ->
            var line = reader.readLine()
            while (line != null) {
                val row = line.split(",").map { cell ->
                    cell.trim()
                }.toMutableList()
                matrix.add(row)
                line = reader.readLine()
            }
        }
        return matrix
    }
}
