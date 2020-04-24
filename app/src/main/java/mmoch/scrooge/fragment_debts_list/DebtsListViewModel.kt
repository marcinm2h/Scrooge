package mmoch.scrooge.fragment_debts_list

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import mmoch.scrooge.database.Debt
import mmoch.scrooge.database.DebtDao


class DebtsListViewModel(private val database: DebtDao) : ViewModel() {
    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val debts = database.list()

    val sum: LiveData<Double> = Transformations.map(debts) {
        if (it.isNotEmpty())
            it.map { debt -> debt.amount }
            .reduce { acc, amount -> acc + amount }
        else 0.0
    }

    private val _navigateToUpdateEvent = MutableLiveData<Int?>()

    val navigateToUpdateEvent: LiveData<Int?>
        get() = _navigateToUpdateEvent

    fun doneNavigatingToUpdate() {
        _navigateToUpdateEvent.value = null
    }

    private val _confirmRemoveEvent = MutableLiveData<Debt?>()

    val confirmRemoveEvent: LiveData<Debt?>
        get() = _confirmRemoveEvent

    fun doneConfirmingRemove() {
        _confirmRemoveEvent.value = null
    }

    fun onItemClick(item: Debt) {
        _navigateToUpdateEvent.value = item.id
    }

    fun onItemLongClick(item: Debt): Boolean {
        _confirmRemoveEvent.value = item
        return true
    }

    fun onDelete(item: Debt) {
        uiScope.launch {
            delete(item)
        }
    }

    private suspend fun delete(debt: Debt) {
        withContext(Dispatchers.IO) {
            database.delete(debt.id)
        }
    }


}