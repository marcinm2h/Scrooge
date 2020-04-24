package mmoch.scrooge.fragment_debts_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import mmoch.scrooge.R
import mmoch.scrooge.database.DebtDatabase
import mmoch.scrooge.databinding.FragmentDebtsListBinding

class DebtsListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDebtsListBinding =
            DataBindingUtil.inflate(inflater,
                R.layout.fragment_debts_list, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = DebtDatabase.getInstance(application).debtDao()
        val viewModelFactory = DebtsListViewModelFactory(dataSource)
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(DebtsListViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        val action = DebtsListFragmentDirections.actionDebtsListFragmentToCreateEditDebtFragment(1)

        binding.createDebtFab.setOnClickListener{
            findNavController().navigate(action)
        }

        return binding.root
    }
}
