package mmoch.scrooge.database

import androidx.annotation.ColorLong
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "debt_table")
data class Debt(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "debtor_name")
    val debtorName: String = "",

    @ColumnInfo(name = "amount")
    val amount: Double = 0.0
)