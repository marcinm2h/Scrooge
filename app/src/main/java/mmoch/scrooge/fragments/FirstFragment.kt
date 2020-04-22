package mmoch.scrooge.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import mmoch.scrooge.R
import mmoch.scrooge.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentFirstBinding =
            DataBindingUtil.inflate(inflater,
                R.layout.fragment_first, container, false)

        binding.button.setOnClickListener{
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        return binding.root
    }
}
