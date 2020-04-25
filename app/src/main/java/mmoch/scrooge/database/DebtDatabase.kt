package mmoch.scrooge.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#6
@Database(entities = [Debt::class], version = 1, exportSchema = false)
abstract class DebtDatabase : RoomDatabase() {
    abstract fun debtDao(): DebtDao

    companion object {
        @Volatile
        private var INSTANCE: DebtDatabase? = null

        fun getInstance(context: Context): DebtDatabase {
            val instance = INSTANCE
            if (instance != null) {
                return instance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DebtDatabase::class.java,
                    "sleep_history_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance

                return instance
            }
        }
    }
}
