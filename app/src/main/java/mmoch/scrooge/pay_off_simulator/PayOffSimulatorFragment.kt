package mmoch.scrooge.pay_off_simulator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import mmoch.scrooge.R
import mmoch.scrooge.databinding.FragmentPayOffSimulatorBinding
import mmoch.scrooge.debts_list.DebtsListFragmentDirections

class PayOffSimulatorFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentPayOffSimulatorBinding =
            DataBindingUtil.inflate(inflater,
                R.layout.fragment_pay_off_simulator, container, false)

        val action = PayOffSimulatorFragmentDirections.actionPayOffSimulatorFragmentToCreateEditDebtFragment(2)

        binding.buttonPrev.setOnClickListener{
            findNavController().navigate(action)
        }

        return binding.root

    }
}
