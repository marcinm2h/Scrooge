package mmoch.scrooge.fragment_create_edit_debt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import mmoch.scrooge.R
import mmoch.scrooge.database.Debt
import mmoch.scrooge.database.DebtDao
import mmoch.scrooge.doubleToString
import mmoch.scrooge.stringToDouble

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

    private val _startShareIntentEvent = MutableLiveData<Debt?>()

    val startShareIntentEvent: LiveData<Debt?>
        get() = _startShareIntentEvent

    fun doneStartedSharingIntent() {
        _startShareIntentEvent.value = null
    }

    private val _backEvent = MutableLiveData<Boolean?>()

    val backEvent: LiveData<Boolean?>
        get() = _backEvent

    fun doneBack() {
        _backEvent.value = null
    }

    private val _confirmBackEvent = MutableLiveData<Debt?>()

    val confirmBackEvent: LiveData<Debt?>
        get() = _confirmBackEvent

    fun doneConfirmingBack() {
        _confirmBackEvent.value = null
    }

    private val _navigateToSimulateEvent = MutableLiveData<Boolean?>()
    val navigateToSimulateEvent: LiveData<Boolean?>
        get() = _navigateToSimulateEvent

    fun doneNavigatingToSimulate() {
        _navigateToSimulateEvent.value = null
    }

    init {
        uiScope.launch {
            if (debtId != null) {
                val debtFromDb = requireNotNull(getById(debtId))
                debt.value = debtFromDb
                debtorNameInput.value = debtFromDb.debtorName
                amountInput.value = doubleToString(debtFromDb.amount)
            }
        }
    }

    fun onSimulate() {
        _navigateToSimulateEvent.value = true
    }

    fun onShare() {
        _startShareIntentEvent.value = requireNotNull(debt.value)
    }

    fun onBackWithConfirm() {
        if (debt.value != null) {
            val amount = requireNotNull(debt.value).amount
            val debtorName = requireNotNull(debt.value).debtorName
            val amountInputValue = stringToDouble(amountInput.value)
            val debtorNameInputValue = debtorNameInput.value
            if (amount != amountInputValue || debtorName != debtorNameInputValue) {
                _confirmBackEvent.value = debt.value
                return
            }
        }

        onBack()
    }

    fun onBack() {
        _backEvent.value = true
    }

    fun onCreate() {
        if (!validateForm(debtorNameInput.value, amountInput.value)) {
            return
        }

        val debtorName = requireNotNull(debtorNameInput.value)
        val amount = stringToDouble(amountInput.value)
        val newDebt = Debt(debtorName = debtorName, amount = amount)

        uiScope.launch {
            insert(newDebt)
        }

        debtorNameInput.value = null
        amountInput.value = null
        _showSnackbarEvent.value = R.string.debt_created_message
    }

    fun onUpdate() {
        if (!validateForm(debtorNameInput.value, amountInput.value)) {
            return
        }

        val debtorName = requireNotNull(debtorNameInput.value)
        val amount = stringToDouble(amountInput.value)
        val updatedDebt =
            Debt(id = requireNotNull(debtId), debtorName = debtorName, amount = amount)

        uiScope.launch {
            update(updatedDebt)
        }

        debt.value = updatedDebt
        _showSnackbarEvent.value = R.string.debt_update_message
    }

    private fun validateForm(debtorNameInput: String?, amountInput: String?): Boolean {
        if (debtorNameInput == null || amountInput == null) {
            _showSnackbarEvent.value = R.string.correct_data
            return false
        }
        return true
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
