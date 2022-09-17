package br.com.t4.artgoproject.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.t4.artgoproject.databinding.FragmentWelcomeBinding

class InstructionFragment : Fragment() {

    private val binding: FragmentWelcomeBinding get() = _binding!!
    private var _binding: FragmentWelcomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentWelcomeBinding.inflate(inflater, container, false).run {
        _binding = this
        binding.root
    }
}