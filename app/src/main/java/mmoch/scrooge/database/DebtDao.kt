package mmoch.scrooge.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DebtDao {
    @Insert
    fun insert(debt: Debt)

    @Update
    fun update(debt: Debt)

    @Query("DELETE FROM debt_table WHERE id = :key")
    fun delete(key: Int)

    @Query("DELETE FROM debt_table")
    fun deleteAll()

    @Query("SELECT * FROM debt_table ORDER BY id DESC")
    fun list(): LiveData<List<Debt>>

    @Query("SELECT * from debt_table WHERE id = :key")
    fun get(key: Int): Debt?
}
