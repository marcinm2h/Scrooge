package mmoch.scrooge.fragment_pay_off_simulator

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
import mmoch.scrooge.R
import mmoch.scrooge.database.DebtDatabase
import mmoch.scrooge.databinding.FragmentPayOffSimulatorBinding

class PayOffSimulatorFragment : Fragment() {
    private val args: PayOffSimulatorFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentPayOffSimulatorBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_pay_off_simulator, container, false
            )

        binding.setLifecycleOwner(this)

        val viewModel =
            createEditDebtViewModel()

        binding.viewModel = viewModel

        viewModel.navigateToEditEvent.observe(viewLifecycleOwner, Observer { debtId ->
            if (debtId != null) {
                val action =
                    PayOffSimulatorFragmentDirections.actionPayOffSimulatorFragmentToCreateEditDebtFragment(
                        debtId
                    )
                findNavController().navigate(action)

                viewModel.doneNavigatingToEdit()
            }
        })

        return binding.root

    }

    private fun createEditDebtViewModel(): PayOffSimulatorViewModel {
        val application = requireNotNull(this.activity).application
        val dataSource = DebtDatabase.getInstance(application).debtDao()
        val viewModelFactory = PayOffSimulatorViewModelFactory(dataSource, args.debtId)

        return ViewModelProviders.of(this, viewModelFactory)
            .get(PayOffSimulatorViewModel::class.java)
    }
}
