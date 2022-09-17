package br.com.t4.artgoproject.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.t4.artgoproject.R
import br.com.t4.artgoproject.databinding.FragmentOnboardingInputBinding
import br.com.t4.artgoproject.extension.navigate
import br.com.t4.onboarding.OnboardingNavigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OnboardingInputDataFragment : Fragment(),AdapterView.OnItemSelectedListener {

    private val binding: FragmentOnboardingInputBinding get() = _binding!!
    private var _binding: FragmentOnboardingInputBinding? = null
    private val args: OnboardingInputDataFragmentArgs by navArgs()
    val listSpinner = arrayListOf("Rock", "Samba", "Rap", "Funk")
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentOnboardingInputBinding.inflate(inflater, container, false).run {
        _binding = this
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        val spinnerData = activity?.applicationContext?.let {
            ArrayAdapter(
                it, android.R.layout.simple_list_item_1,
                listSpinner
            )
        }
        spinnerData?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnGenderMusic.apply {
            adapter = spinnerData
        }
    }

    private fun registerUser() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                setUserData(
                    mapOf(
                        "id" to FirebaseAuth.getInstance().uid.toString(),
                        "nome" to binding.edtName.editText?.text.toString(),
                        "userName" to binding.edtUserName.editText?.text.toString(),
                        "email" to binding.edtEmail.editText?.text.toString(),
                        "celular" to binding.edtCelular.editText?.text.toString(),
                        "cidade" to binding.edtCity.editText?.text.toString(),
                        "generoMusical" to binding.spnGenderMusic.selectedItem.toString(),
                    )
                )
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(activity, "Não foi possivel criar sua conta", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun recoveryUserData(): String {
        firebaseAuth = FirebaseAuth.getInstance()
        return firebaseAuth.currentUser?.uid.toString()
    }

    private fun setUserData(userData: Map<String, String>) {
        val db = Firebase.firestore
        db.collection("users")
            .document(recoveryUserData())
            .set(userData)
            .addOnSuccessListener {
                navigateToNext()
            }
            .addOnFailureListener {
                Toast.makeText(activity, "", Toast.LENGTH_LONG).show()
            }
    }

    private fun setupListeners() {
        binding.btnNext.setOnClickListener {
            validateInputUsers()
        }
    }

    private fun validateInputUsers() = with(binding) {
        if (edtCelular.editText?.checkIsNotEmpty() == true
            && edtCity.editText?.checkIsNotEmpty() == true
            && edtEmail.editText?.checkIsNotEmpty() == true
            && edtName.editText?.checkIsNotEmpty() == true
            && edtUserName.editText?.checkIsNotEmpty() == true
        ) {
            registerUser()
        } else {
            Toast.makeText(activity, "Todos os dados precisam estar preenchidos", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun EditText.checkIsNotEmpty() =
        this.text?.run { isNotEmpty() }

    private fun navigateToNext() {
        navigate("LIST_CONTACT")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Toast.makeText(activity, "Selected language: "+ listSpinner[position], Toast.LENGTH_LONG).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        //
    }
}