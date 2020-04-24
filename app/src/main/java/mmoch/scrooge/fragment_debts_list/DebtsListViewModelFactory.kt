package mmoch.scrooge.fragment_debts_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mmoch.scrooge.database.DebtDao

class DebtsListViewModelFactory(
    private val dataSource: DebtDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DebtsListViewModel::class.java)) {
            return DebtsListViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

