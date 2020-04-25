package mmoch.scrooge.fragment_pay_off_simulator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mmoch.scrooge.database.DebtDao

class PayOffSimulatorViewModel(
    private val database: DebtDao,
    private val debtId: Int
) : ViewModel() {
    private val _navigateToEditEvent = MutableLiveData<Int?>()

    val navigateToEditEvent: LiveData<Int?>
        get() = _navigateToEditEvent

    fun doneNavigatingToEdit() {
        _navigateToEditEvent.value = null
    }

    fun onBack() {
        _navigateToEditEvent.value = debtId
    }

}