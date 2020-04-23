package mmoch.scrooge.debts_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import mmoch.scrooge.R
import mmoch.scrooge.databinding.FragmentDebtsListBinding

class DebtsListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDebtsListBinding =
            DataBindingUtil.inflate(inflater,
                R.layout.fragment_debts_list, container, false)

        binding.buttonNext.setOnClickListener{
            findNavController().navigate(R.id.action_debtsListFragment_to_createEditDebtFragment)
        }

        return binding.root
    }
}
