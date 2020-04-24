package mmoch.scrooge.fragment_create_edit_debt

import android.content.Intent
import android.os.Bundle
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
import mmoch.scrooge.R
import mmoch.scrooge.database.DebtDatabase
import mmoch.scrooge.databinding.FragmentCreateEditDebtBinding

class CreateEditDebtFragment : Fragment() {
    val args: CreateEditDebtFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCreateEditDebtBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_create_edit_debt, container, false
            )

        val application = requireNotNull(this.activity).application
        val dataSource = DebtDatabase.getInstance(application).debtDao()
        val debtId = if (args.debtId > 0) args.debtId else null
        val viewModelFactory = CreateEditDebtViewModelFactory(dataSource, debtId)
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(CreateEditDebtViewModel::class.java)

        binding.viewModel = viewModel

        binding.setLifecycleOwner(this)

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

        binding.simulateButton.setOnClickListener {
            findNavController().navigate(R.id.action_createEditDebtFragment_to_payOffSimulatorFragment)
        }

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_createEditDebtFragment_to_debtsListFragment)
        }


        return binding.root
    }
}
