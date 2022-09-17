package br.com.t4.artgoproject.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.t4.artgoproject.R
import br.com.t4.artgoproject.databinding.FragmentOnboardingTypeBinding
import br.com.t4.artgoproject.extension.navigate
import br.com.t4.onboarding.OnboardingNavigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class OnboardingSelectTypeFragment : Fragment() {

    private val binding: FragmentOnboardingTypeBinding get() = _binding!!
    private var _binding: FragmentOnboardingTypeBinding? = null
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentOnboardingTypeBinding.inflate(inflater, container, false).run {
        _binding = this
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun recoveryUserData(): String {
        firebaseAuth = FirebaseAuth.getInstance()
        return firebaseAuth.currentUser?.uid.toString()
    }

    private fun setupRecyclerView() = with(binding) {
        rvType.apply {
            adapter = TypeAdapter(listAdapter) { position ->
                if (position == 0)
                    setUserTypeData("artista")
                else
                    setUserTypeData("contratante")
            }
        }
    }

    private fun setUserTypeData(type: String) {
        val db = Firebase.firestore
        db.collection("users")
            .document(recoveryUserData())
            .set(mapOf("type" to type))
            .addOnSuccessListener {
                handlerNavigateToNextStep()
            }
            .addOnFailureListener {
                it.message
                Toast.makeText(activity, "", Toast.LENGTH_LONG).show()
            }
    }

    private fun handlerNavigateToNextStep() {
        navigate(OnboardingNavigation.ONBOARDING_DATA)
    }

    companion object {
        val listAdapter = listOf(
            TypeModel(
                R.string.iam_music_title,
                R.string.iam_music_subtitle,
                R.drawable.ic_guitar,
            ),
            TypeModel(
                R.string.iam_contract_title,
                R.string.iam_contract_subtitle,
                R.drawable.ic_money,
            )
        )
    }
}