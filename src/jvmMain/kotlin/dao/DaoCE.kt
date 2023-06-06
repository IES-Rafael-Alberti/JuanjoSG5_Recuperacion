package dao

import models.CE
import models.Unidad
import railway.Result
import railway.Results
import javax.sql.DataSource

class DaoCE(private var dataSource: DataSource):Dao<CE, Results> {
    override fun createTable(): Result<Unit, Results> {
        TODO("Not yet implemented")
    }

    override fun deleteTable(): Result<Unit, Results> {
        TODO("Not yet implemented")
    }

    override fun getById(id: Int): Result<CE?, Results> {
        TODO("Not yet implemented")
    }

    override fun getAll(): Result<List<CE>, Results> {
        TODO("Not yet implemented")
    }

    override fun deleteEntity(entity: CE): Result<CE, Results> {
        TODO("Not yet implemented")
    }

    override fun updateEntity(entity: CE): Result<CE, Results> {
        TODO("Not yet implemented")
    }

    override fun createEntity(entity: CE): Result<CE, Results> {
        TODO("Not yet implemented")
    }
}