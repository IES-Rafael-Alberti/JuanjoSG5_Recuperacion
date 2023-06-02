package dao

interface Dao<T>  {
    fun createTable()
    fun deleteTable()
    fun getById(id: Int): T?
    fun getAll(): List<T>
    fun createEntity(entity: T): Int?
    fun updateEntity(entity: T): Int?
    fun deleteEntity(entity: T): Int?
}

