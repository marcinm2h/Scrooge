package mmoch.scrooge

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import mmoch.scrooge.database.Debt
import mmoch.scrooge.database.DebtDao
import mmoch.scrooge.database.DebtDatabase
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DebtDatabaseTest {
    private lateinit var debtDao: DebtDao
    private lateinit var debtDatabase: DebtDatabase

    @Before
    fun createDatabase() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        debtDatabase = Room.inMemoryDatabaseBuilder(context, DebtDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        debtDao = debtDatabase.debtDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        debtDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGet() {
        val debt = Debt(id = 1 ,debtorName = "Marcin", amount = 100.0)
        debtDao.insert(debt)
        val debtFromDatabase = debtDao.get(1)
        assertEquals(debtFromDatabase?.debtorName, "Marcin")
    }
}

