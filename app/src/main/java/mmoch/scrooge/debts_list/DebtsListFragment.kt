package mmoch.scrooge.debts_list

import android.os.Bundle
import android.text.Layout
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

        val action = DebtsListFragmentDirections.actionDebtsListFragmentToCreateEditDebtFragment(1)

        binding.buttonNext.setOnClickListener{
            findNavController().navigate(action)
        }

        return binding.root
    }
}
