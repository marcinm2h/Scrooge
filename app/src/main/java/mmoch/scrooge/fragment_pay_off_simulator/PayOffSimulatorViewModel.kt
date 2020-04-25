package mmoch.scrooge.fragment_pay_off_simulator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import mmoch.scrooge.R
import mmoch.scrooge.database.Debt
import mmoch.scrooge.database.DebtDao
import mmoch.scrooge.doubleToString
import mmoch.scrooge.stringToDouble
import java.util.*
import kotlin.concurrent.timerTask
import kotlin.math.max

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

    val debtAmount = MutableLiveData<Double?>(null)

    val debtorName = MutableLiveData<String?>(null)

    val velocityInput = MutableLiveData<String?>(null)

    val interestsRateInput = MutableLiveData<String?>(null)

    val accumulatedInterests = MutableLiveData<Double?>(null)

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
            debtorName.value = debtFromDb.debtorName
            debtAmount.value = debtFromDb.amount
        }
    }


    private suspend fun getById(debtId: Int): Debt? {
        return withContext(Dispatchers.IO) {
            return@withContext database.get(debtId)
        }
    }

    private fun onTick() {
        Timer().schedule(timerTask {
            val nextAmount = max(
                debtAmount.value!!.minus(
                    stringToDouble(velocityInput.value)
                ), 0.0
            ).let { nextAmount ->
                if (nextAmount > 0) {
                    val interests = stringToDouble(
                        doubleToString(
                            nextAmount.times(
                                stringToDouble(interestsRateInput.value) / 100
                            )
                        )
                    )
                    accumulatedInterests.postValue(
                        interests.plus(accumulatedInterests.value ?: 0.0)
                    )
                    nextAmount + interests
                } else {
                    nextAmount
                }
            }

            debtAmount.postValue(
                nextAmount
            )

            if (nextAmount > 0) {
                onTick()
            }

        }, 1000)
    }

    fun onStart() {
        onTick()
    }

    fun onStartButtonClick() {
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