package mmoch.scrooge.fragment_pay_off_simulator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import mmoch.scrooge.R
import mmoch.scrooge.database.Debt
import mmoch.scrooge.database.DebtDao

class PayOffSimulatorViewModel(
    private val database: DebtDao,
    private val debtId: Int
) : ViewModel() {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToEditEvent = MutableLiveData<Int?>()

    val navigateToEditEvent: LiveData<Int?>
        get() = _navigateToEditEvent

    fun doneNavigatingToEdit() {
        _navigateToEditEvent.value = null
    }

    private val debt = MutableLiveData<Debt?>(null)

    val formattedDebt: LiveData<String> = Transformations.map(debt) {
        "${it?.debtorName}: ${it?.amount}"
    }

    val velocityInput = MutableLiveData<String?>(null)

    val interestsRateInput = MutableLiveData<String?>(null)

    private val _simulationStarted = MutableLiveData<Boolean>(false)
    val simulationStarted: LiveData<Boolean>
    get() = _simulationStarted

    private val _showSnackbarEvent = MutableLiveData<Int?>()

    val showSnackbarEvent: LiveData<Int?>
        get() = _showSnackbarEvent

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = null
    }


    init {
        uiScope.launch {
            val debtFromDb = requireNotNull(getById(debtId))
            debt.value = debtFromDb
        }
    }


    private suspend fun getById(debtId: Int): Debt? {
        return withContext(Dispatchers.IO) {
            return@withContext database.get(debtId)
        }
    }

    fun onStart() {
        if (!validateForm(velocityInput.value, interestsRateInput.value)) {
            return
        }
        _simulationStarted.value = true
    }

    private fun validateForm(velocityInput: String?, interestsRateInput: String?): Boolean {
        if (velocityInput == null || interestsRateInput == null) {
            _showSnackbarEvent.value = R.string.correct_data
            return false
        }
        return true
    }

    fun onBack() {
        _navigateToEditEvent.value = debtId
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}