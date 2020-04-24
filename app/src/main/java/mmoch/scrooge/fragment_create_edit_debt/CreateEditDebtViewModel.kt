package mmoch.scrooge.fragment_create_edit_debt

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import mmoch.scrooge.database.Debt
import mmoch.scrooge.database.DebtDao

class CreateEditDebtViewModel(private val database: DebtDao): ViewModel() {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val debtorNameInput =  MutableLiveData<String>()
    val amountInput =  MutableLiveData<String>()

    fun onCreate() {
        val debtorName = debtorNameInput.value.toString()
        val amount = amountInput.value?.toDouble() ?: 0.0 //FIXME: required

        uiScope.launch {
            val debt = Debt(debtorName = debtorName, amount = amount)
            insert(debt)
        }
    }

//    private suspend fun update(debt: Debt) {
//        withContext(Dispatchers.IO) {
//            database.update(debt)
//        }
//    }

    private suspend fun insert(debt: Debt) {
        withContext(Dispatchers.IO) {
            database.insert(debt)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
