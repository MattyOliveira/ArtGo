package br.com.t4.artgoproject.ui.listcontact

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.t4.artgoproject.databinding.FragmentListContactListBinding
import br.com.t4.artgoproject.extension.navigate
import br.com.t4.artgoproject.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

class ListContactFragment : Fragment() {

    private val binding: FragmentListContactListBinding get() = _binding
    private lateinit var _binding: FragmentListContactListBinding

    private var adapterListContact: MyItemRecyclerViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentListContactListBinding.inflate(inflater, container, false).run {
        _binding = this
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterListContact = MyItemRecyclerViewAdapter {
            navigate("CHAT/$it")
        }
        configRecyclerView()
        fetchUsers()
    }

    override fun onStart() {
        super.onStart()
        binding.list.adapter = adapterListContact
    }

    private fun configRecyclerView() {
    }

    private fun fetchUsers() {
        FirebaseFirestore.getInstance().collection("/users")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.e("Teste", error.message, error)
                        return
                    }
                    val docs = value!!.documents
                    adapterListContact?.clear()
                    for (doc in docs) {
                        val user = doc.toObject(User::class.java)
                        val uid = FirebaseAuth.getInstance().uid
                        if (user?.id == uid)
                            continue
                        if (user != null) {
                            adapterListContact?.addData(user)
                        }
                        adapterListContact?.notifyDataSetChanged()
                    }
                }
            })
    }
}