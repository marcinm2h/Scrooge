package mmoch.scrooge.fragment_pay_off_simulator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mmoch.scrooge.database.DebtDao

class PayOffSimulatorViewModelFactory(
    private val dataSource: DebtDao,
    private val debtId: Int
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PayOffSimulatorViewModel::class.java)) {
            return PayOffSimulatorViewModel(dataSource, debtId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

