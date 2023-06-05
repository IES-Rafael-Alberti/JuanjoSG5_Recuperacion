package dao.Unidad

import dao.Dao
import dataFactory.DataFactory
import log.Log
import models.Unidad
import railway.Result
import railway.Results
import java.sql.SQLException
import javax.sql.DataSource

class DaoUnidad(private var dataSource: DataSource):Dao<Unidad,Results> {
    override fun createEntity(unidad: Unidad): Result<Unidad, Results> {
        val sql = "INSERT INTO Unidad (nombre) VALUES (?)"

            dataSource.connection.use { conn ->
                Log.info("fuck2")
                conn.prepareStatement(sql).use { stmt ->
                    Log.info("fuck3")
                    stmt.setString(1, unidad.nombre)
                    try {
                        Log.info("DaoUnidad.createEntity -> Executing query")
                    stmt.executeUpdate()
                } catch (e: Exception) {
                    Log.warning("DaoUnidad.createEntity -> $e")
                    return Result(unidad, Results.FAILURE)
                }
                    return Result(unidad, Results.SUCCESSFUL)
                }
            }
    }

    override fun getById(id: Int): Unidad? {
        val sql = "SELECT * FROM Unidad WHERE ID = ?"
        var unidad: Unidad? = null
        dataSource.connection.use {conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, id.toString())
                val rs = stmt.executeQuery()
                if (rs.next()) {
                    unidad = Unidad(
                        nombre = rs.getString("nombre")
                    )
                }else {
                    null
                }
            }
        }
        return unidad
    }

    override fun getAll(): List<Unidad> {
        val sql = "SELECT * FROM Unidad"
        val result = mutableListOf<Unidad>()

        try {
            dataSource.connection.use { conn ->
                conn.prepareStatement(sql).use { stmt ->
                    val resultSet = stmt.executeQuery()

                    while (resultSet.next()) {
                        val nombre = resultSet.getString("nombre")

                        val unid = Unidad(nombre)
                        result.add(unid)
                    }
                }
            }
        } catch (e: SQLException) {
            // Handle the exception
            e.printStackTrace()
            // Return an appropriate value or throw a custom exception
            return emptyList()
        }

        return result
    }

    override fun deleteEntity(unidad: Unidad): Int? {
        val sql = "DELETE FROM Unidad WHERE nombre = ?"
        try {
            dataSource.connection.use { conn ->
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, unidad.nombre)
                    return stmt.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            // Handle the exception
            e.printStackTrace()
            return -1
        }
    }
    override fun updateEntity(unid: Unidad): Int {
        val sql = "UPDATE Unidad SET nombre = ? WHERE nombre = ?"
        try {
            dataSource.connection.use { conn ->
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, unid.nombre)
                    return stmt.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            // Handle the exception
            e.printStackTrace()
            return -1
        }
    }


    override fun createTable() {
        val sql =
            "CREATE TABLE Unidad (" +
                    "nombre VARCHAR2(50) PRIMARY KEY); "
        dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.executeUpdate()
                Log.info("Tabla Unidad creada")
            }
        }
    }
    override fun deleteTable() {
        val sql ="DROP TABLE Unidad;"
        dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.executeUpdate()
            }
        }
    }
}

fun main(){
    val source = DataFactory.getDataSource(DataFactory.DataSourceType.Embedded)
    val dod = DaoUnidad(source)
    dod.createTable()
    dod.createEntity(Unidad("test"))
    dod.createEntity(Unidad("test2"))
    println(DaoUnidad(source).getAll().toString())
    Log.info("fuccccccccccccccccccccccckkkkkkkkkkkkk")

}


