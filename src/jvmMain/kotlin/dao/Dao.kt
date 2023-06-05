package dao

import models.Unidad
import railway.Result

interface Dao<T,U> {
    fun createTable(): Result<Unit, U>
    fun deleteTable(): Result<Unit, U>
    fun getById(id: Int): Result<T, U>
    fun getAll(): Result<List<T>, U>
    fun createEntity(entity: T): Result<T, U>
    fun updateEntity(entity: T): Result<T, U>
    fun deleteEntity(entity: T): Result<T, U>
}


