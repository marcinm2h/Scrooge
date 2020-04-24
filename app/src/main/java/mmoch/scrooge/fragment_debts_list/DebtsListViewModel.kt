package mmoch.scrooge.fragment_debts_list

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import mmoch.scrooge.database.Debt
import mmoch.scrooge.database.DebtDao
import java.util.logging.Logger


class DebtsListViewModel(private val database: DebtDao) : ViewModel() {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val debts = database.list()

    val sum: LiveData<Double> = Transformations.map(debts) {
        it.map { debt -> debt.amount }
            .reduce { acc, amount -> acc + amount }
    }


    fun onItemClick(item: Debt) {
        println("CLICK -- NORMAL")
    }

    fun onItemLongClick(item: Debt): Boolean {
        println("CLICK -- LONG")
        return true
    }


}