package mmoch.scrooge.fragment_create_edit_debt

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import mmoch.scrooge.R
import mmoch.scrooge.database.Debt
import mmoch.scrooge.database.DebtDao

class CreateEditDebtViewModel(
    private val database: DebtDao,
    private val debtId: Int?
) : ViewModel() {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val debtorNameInput = MutableLiveData<String>()

    val amountInput = MutableLiveData<String>()

    val debt = MutableLiveData<Debt?>(null)

    val editType: LiveData<Boolean> = Transformations.map(debt) {
        it != null
    }

    private val _showSnackbarEvent = MutableLiveData<Int?>()

    val showSnackbarEvent: LiveData<Int?>
        get() = _showSnackbarEvent

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = null
    }

    init {
        uiScope.launch {
            if (debtId != null) {
                val debtFromDb = requireNotNull(getById(debtId))
                debt.value = debtFromDb
                debtorNameInput.value = debtFromDb.debtorName
                amountInput.value = debtFromDb.amount.toString()
            }
        }
    }

    fun onCreate() {
        val debtorName = debtorNameInput.value.toString()
        val amount = amountInput.value?.toDouble() ?: 0.0 //FIXME: required

        uiScope.launch {
            val debt = Debt(debtorName = debtorName, amount = amount)
            insert(debt)
        }

        // FIXME: onDebtorCreated
        debtorNameInput.value = null
        amountInput.value = null
        _showSnackbarEvent.value = R.string.debt_created_message
    }

    fun onUpdate() {
        val debtorName = debtorNameInput.value.toString()
        val amount = amountInput.value?.toDouble() ?: 0.0 //FIXME: required

        uiScope.launch {
            val debt = Debt(id = requireNotNull(debtId), debtorName = debtorName, amount = amount)
            update(debt)
        }

        _showSnackbarEvent.value = R.string.debt_update_message
    }

    private suspend fun getById(debtId: Int): Debt? {
        return withContext(Dispatchers.IO) {
            return@withContext database.get(debtId)
        }
    }

    private suspend fun update(debt: Debt) {
        withContext(Dispatchers.IO) {
            database.update(debt)
        }
    }

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
