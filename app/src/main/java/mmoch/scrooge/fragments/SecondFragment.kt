package mmoch.scrooge.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import mmoch.scrooge.R
import mmoch.scrooge.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSecondBinding =
            DataBindingUtil.inflate(inflater,
                R.layout.fragment_second, container, false)

        binding.button.setOnClickListener{
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        return binding.root

    }
}
