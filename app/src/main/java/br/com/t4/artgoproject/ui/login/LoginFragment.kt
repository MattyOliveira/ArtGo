package br.com.t4.artgoproject.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.t4.artgoproject.databinding.FragmentLoginBinding
import br.com.t4.artgoproject.extension.navigate
import br.com.t4.login.LoginNavigate
import br.com.t4.onboarding.OnboardingNavigation
import com.google.firebase.auth.FirebaseAuth

class LoginFragment: Fragment() {

    private lateinit var _binding: FragmentLoginBinding
    private val binding: FragmentLoginBinding get() = _binding

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentLoginBinding.inflate(inflater, container, false).run {
        _binding = this
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.textView.setOnClickListener {
            startRegisterUser()
        }
        binding.button.setOnClickListener {
            login()
        }
    }

    private fun startRegisterUser() {
        navigate(OnboardingNavigation.ONBOARDING_START)
    }

    private fun login(){
        firebaseAuth.signInWithEmailAndPassword(
            binding.edtLogin.editText?.text.toString(),
            binding.edtPassword.editText?.text.toString(),
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    activity,
                    "Login efetuado com sucesso",
                    Toast.LENGTH_LONG
                ).show()

                navigate("LIST_CONTACT")
            } else {
                Toast.makeText(activity,
                    "Login falhou!!",
                    Toast.LENGTH_LONG)
                    .show();
            }
        }
    }
}