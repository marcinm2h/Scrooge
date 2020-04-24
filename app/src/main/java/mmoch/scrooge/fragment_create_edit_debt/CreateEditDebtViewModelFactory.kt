package mmoch.scrooge.fragment_create_edit_debt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mmoch.scrooge.database.DebtDao

class CreateEditDebtViewModelFactory(
    private val dataSource: DebtDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateEditDebtViewModel::class.java)) {
            return CreateEditDebtViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

