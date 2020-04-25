package mmoch.scrooge.fragment_debts_list

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_debts_list, container, false
            )

        binding.setLifecycleOwner(this)

        val viewModel =
            createDebtsListViewModel()

        val adapter = DebtAdapter(viewModel)

        binding.viewModel = viewModel

        binding.debtsListRecycler.adapter = adapter

        viewModel.navigateToCreateEvent.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val action =
                    DebtsListFragmentDirections.actionDebtsListFragmentToCreateEditDebtFragment(-1)
                findNavController().navigate(action)
                viewModel.doneNavigatingToCreate()
            }
        })

        viewModel.items.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.navigateToUpdateEvent.observe(viewLifecycleOwner, Observer { debtId ->
            if (debtId != null) {
                val action =
                    DebtsListFragmentDirections.actionDebtsListFragmentToCreateEditDebtFragment(
                        debtId
                    )
                findNavController().navigate(action)
                viewModel.doneNavigatingToUpdate()
            }
        })

        viewModel.confirmRemoveEvent.observe(viewLifecycleOwner, Observer { debt ->
            if (debt != null) {
                AlertDialog.Builder(context)
                    .setMessage(getString(R.string.dialog_confirm_remove))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.yes)) { dialog, id ->
                        viewModel.onDelete(debt)
                    }
                    .setNegativeButton(getString(R.string.no)) { dialog, id ->
                        dialog.cancel()
                    }.create().show()

                viewModel.doneConfirmingRemove()
            }
        })

        return binding.root
    }

    private fun createDebtsListViewModel(): DebtsListViewModel {
        val application = requireNotNull(this.activity).application
        val dataSource = DebtDatabase.getInstance(application).debtDao()
        val viewModelFactory = DebtsListViewModelFactory(dataSource)

        return ViewModelProviders.of(this, viewModelFactory).get(DebtsListViewModel::class.java)
    }
}
