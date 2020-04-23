package mmoch.scrooge.fragment_create_edit_debt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import mmoch.scrooge.R
import mmoch.scrooge.databinding.FragmentCreateEditDebtBinding

class CreateEditDebtFragment : Fragment() {
    val args: CreateEditDebtFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCreateEditDebtBinding =
            DataBindingUtil.inflate(inflater,
                R.layout.fragment_create_edit_debt, container, false)


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
