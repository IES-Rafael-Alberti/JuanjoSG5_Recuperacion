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
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, unidad.nombre)
                try {
                    Log.info("DaoUnidad.createEntity -> Executing query")
                    stmt.executeUpdate()
            } catch (e: Exception) {
                Log.warning("DaoUnidad.createEntity -> $e")
                return Result(unidad, Results.FAILURE)
            }
                Log.info("DaoUnidad.createEntity -> Query executed")
                return Result(unidad, Results.SUCCESSFUL)
            }
        }
    }

    override fun getById(id: Int): Result<Unidad?, Results> {
        val sql = "SELECT * FROM Unidad WHERE ID = ?"
        var unidad: Unidad? = null
        dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, id)
                try {
                    Log.info("DaoUnidad.getById -> Executing query")
                    val rs = stmt.executeQuery()
                    if (rs.next()) {
                        unidad = Unidad(
                                nombre = rs.getString("nombre")
                        )
                    }
                    Log.info("DaoUnidad.getById -> Query executed")
                    return Result(unidad, Results.SUCCESSFUL)
                } catch (e: Exception) {
                    Log.warning("DaoUnidad.getById -> $e")
                    return Result(unidad, Results.FAILURE)
                }
            }
        }
    }

    override fun getAll(): Result<List<Unidad>, Results> {
        val sql = "SELECT * FROM Unidad"
        val result = mutableListOf<Unidad>()
        dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                try {
                    Log.info("DaoUnidad.getAll -> Executing query")
                    val resultSet = stmt.executeQuery()
                    while (resultSet.next()) {
                        val nombre = resultSet.getString("nombre")

                        val unid = Unidad(nombre)
                        result.add(unid)
                    }
                    Log.info("DaoUnidad.getAll -> Query executed")
                    return Result(result, Results.SUCCESSFUL)
                } catch (e: SQLException) {
                    Log.warning("DaoUnidad.getAll -> $e")
                    return Result(emptyList(), Results.FAILURE)
                }
            }
        }
    }

    override fun deleteEntity(entity: Unidad): Result<Unidad, Results> {
        TODO("Not yet implemented")
    }


    override fun updateEntity(unidad: Unidad): Result<Unidad, Results> {
        val sql = "UPDATE Unidad SET nombre = ? WHERE nombre = ?"
        dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, unidad.nombre)
                try {
                    Log.info("DaoUnidad.updateEntity -> Executing Update")
                    val rowsAffected = stmt.executeUpdate()
                    if (rowsAffected > 0) {
                        Log.info("DaoUnidad.updateEntity -> $rowsAffected rows updated")
                        return Result(unidad, Results.SUCCESSFUL)
                    } else {
                        Log.info("DaoUnidad.updateEntity -> 0 rows updated")
                        return Result(unidad, Results.FAILURE)
                    }
                }catch (e: SQLException) {
                        Log.warning("DaoUnidad.updateEntity -> $e")
                        return Result(unidad, Results.FAILURE)
                }
            }
        }
    }




    override fun createTable(): Result<Unit, Results> {
        val sql = "CREATE TABLE Unidad (nombre VARCHAR2(50) PRIMARY KEY);"
        dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                try {
                    stmt.executeUpdate()
                    Log.info("Tabla Unidad creada")
                    return Result(Unit, Results.SUCCESSFUL)
                }catch (e: SQLException) {
                        Log.warning("DaoUnidad.createTable -> $e")
                        return Result(Unit, Results.FAILURE)
                    }
            }
        }

    }

    override fun deleteTable(): Result<Unit, Results> {
        val sql = "DROP TABLE Unidad;"
        dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                try {
                    stmt.executeUpdate()
                    return Result(Unit, Results.SUCCESSFUL)
                } catch (e: SQLException) {
                    Log.warning("DaoUnidad.deleteTable -> $e")
                    return Result(Unit, Results.FAILURE)
                }
            }
        }

    }

}

fun main(){
    val source = DataFactory.getDataSource(DataFactory.DataSourceType.Embedded)
    val dod = DaoUnidad(source)
    dod.createTable()
    val uni2 = dod.createEntity(Unidad("test"))
    val uni = dod.createEntity(Unidad("test2"))
    
    dod.getAll()
    dod.updateEntity(uni2.obj)
    println(DaoUnidad(source).getAll().toString())
}


