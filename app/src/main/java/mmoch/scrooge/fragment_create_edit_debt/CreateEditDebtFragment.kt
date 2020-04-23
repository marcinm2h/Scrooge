package mmoch.scrooge.fragment_create_edit_debt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import mmoch.scrooge.R
import mmoch.scrooge.database.DebtDatabase
import mmoch.scrooge.databinding.FragmentCreateEditDebtBinding
import mmoch.scrooge.fragment_debts_list.DebtsListViewModel
import mmoch.scrooge.fragment_debts_list.DebtsListViewModelFactory

class CreateEditDebtFragment : Fragment() {
    val args: CreateEditDebtFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCreateEditDebtBinding =
            DataBindingUtil.inflate(inflater,
                R.layout.fragment_create_edit_debt, container, false)



        val application = requireNotNull(this.activity).application
        val dataSource = DebtDatabase.getInstance(application).debtDao()
        val viewModelFactory = CreateEditDebtViewModelFactory(dataSource)
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(CreateEditDebtViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        binding.onCreateButton.setOnClickListener{
            viewModel.onCreate()
        }

        binding.titleText.text = args.debtId.toString()

        binding.buttonNext.setOnClickListener{
            findNavController().navigate(R.id.action_createEditDebtFragment_to_payOffSimulatorFragment)
        }

        binding.buttonPrev.setOnClickListener{
            findNavController().navigate(R.id.action_createEditDebtFragment_to_debtsListFragment)
        }

        return binding.root
    }
}
