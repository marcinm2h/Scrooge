package mmoch.scrooge.fragment_create_edit_debt

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import mmoch.scrooge.DecimalDigitsInputFilter
import mmoch.scrooge.R
import mmoch.scrooge.database.DebtDatabase
import mmoch.scrooge.databinding.FragmentCreateEditDebtBinding

class CreateEditDebtFragment : Fragment() {
    private val args: CreateEditDebtFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCreateEditDebtBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_create_edit_debt, container, false
            )

        binding.setLifecycleOwner(this)

        val viewModel =
            createEditDebtViewModel()

        binding.viewModel = viewModel

        binding.amountInput.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter(2))


        viewModel.showSnackbarEvent.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    it,
                    Snackbar.LENGTH_SHORT
                ).show()
                viewModel.doneShowingSnackbar()
            }
        })

        viewModel.startShareIntentEvent.observe(viewLifecycleOwner, Observer { debt ->
            if (debt != null) {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        getString(R.string.share_debt, debt.debtorName, debt.amount)
                    )
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
                viewModel.doneStartedSharingIntent()
            }
        })

        viewModel.backEvent.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val action =
                    CreateEditDebtFragmentDirections.actionCreateEditDebtFragmentToDebtsListFragment()
                findNavController().navigate(action)
                viewModel.doneBack()
            }
        })

        viewModel.confirmBackEvent.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                AlertDialog.Builder(context)
                    .setMessage(getString(R.string.dialog_confirm_back))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.yes)) { dialog, id ->
                        viewModel.onBack()
                    }
                    .setNegativeButton(getString(R.string.no)) { dialog, id ->
                        dialog.cancel()
                    }.create().show()
                viewModel.doneConfirmingBack()
            }
        })

        viewModel.navigateToSimulateEvent.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val action =
                    CreateEditDebtFragmentDirections.actionCreateEditDebtFragmentToPayOffSimulatorFragment(
                        args.debtId
                    )
                findNavController().navigate(action)
            }
        })

        return binding.root
    }

    private fun createEditDebtViewModel(): CreateEditDebtViewModel {
        val application = requireNotNull(this.activity).application
        val dataSource = DebtDatabase.getInstance(application).debtDao()
        val debtId = if (args.debtId > 0) args.debtId else null
        val viewModelFactory = CreateEditDebtViewModelFactory(dataSource, debtId)

        return ViewModelProviders.of(this, viewModelFactory)
            .get(CreateEditDebtViewModel::class.java)
    }
}
