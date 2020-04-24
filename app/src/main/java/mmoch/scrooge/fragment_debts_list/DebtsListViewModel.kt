package mmoch.scrooge.fragment_debts_list

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import mmoch.scrooge.database.Debt
import mmoch.scrooge.database.DebtDao

class DebtsListViewModel(private val database: DebtDao): ViewModel() {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var debts = database.list()
    val debtsString = Transformations.map(debts) { debts ->
        debts.map { debt -> "${debt.debtorName}: ${debt.amount}\n\n" }.joinToString()
    }

    fun onItemClick(item: Debt) {
        println(item)
    }

}