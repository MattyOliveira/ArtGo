package br.com.t4.artgoproject.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import br.com.t4.artgoproject.databinding.FragmentOnboardingStartBinding
import br.com.t4.artgoproject.extension.navigate
import br.com.t4.onboarding.OnboardingNavigation
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class OnboardingStart : Fragment() {

    private val binding: FragmentOnboardingStartBinding get() = _binding!!
    private var _binding: FragmentOnboardingStartBinding? = null

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentOnboardingStartBinding.inflate(inflater, container, false).run {
        _binding = this
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnStart.setOnClickListener {
            validatePassword()
        }
    }

    private fun validatePassword() {
        if (binding.edtPassword.editText?.text?.isEmpty() == false) {
            registerUser()
        } else {
            Toast.makeText(activity, "As senhas não correspondem", Toast.LENGTH_LONG).show()
        }
    }

    private fun registerUser() {
        activity?.applicationContext?.let { FirebaseApp.initializeApp(it) }
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.createUserWithEmailAndPassword(
            binding.edtEmail.editText?.text.toString(),
            binding.edtPassword.editText?.text.toString()
        ).addOnSuccessListener {
            navigate(OnboardingNavigation.ONBOARDING_TYPE)
            bundleOf("id" to firebaseAuth.currentUser?.uid)
        }.addOnFailureListener {
            it.stackTrace
            Toast.makeText(activity, "Não conseguimos criar sua conta entre em contato com o suporte",Toast.LENGTH_LONG).show()
        }
    }
}