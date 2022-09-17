package br.com.t4.artgoproject.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.t4.artgoproject.databinding.FragmentOnboardingPriceBinding
import br.com.t4.artgoproject.extension.navigate
import br.com.t4.onboarding.OnboardingNavigation

class OnboardingInputPriceFragment : Fragment() {

    private val binding: FragmentOnboardingPriceBinding get() = _binding!!
    private var _binding: FragmentOnboardingPriceBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentOnboardingPriceBinding.inflate(inflater, container, false).run {
        _binding = this
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnFinish.setOnClickListener {
            navigate(OnboardingNavigation.ONBOARDING_INSTRUCTIONS)
        }
    }
}