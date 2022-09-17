package br.com.t4.artgoproject.ui.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.t4.artgoproject.databinding.FragmentChatBinding
import br.com.t4.artgoproject.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ChatFragment : Fragment() {

    private val binding: FragmentChatBinding get() = _binding
    private lateinit var _binding: FragmentChatBinding
    val safeArgs: ChatFragmentArgs by navArgs()


    private var adapterChat: ChatAdapter? = null
    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentChatBinding.inflate(inflater, container, false).run {
        _binding = this
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterChat = ChatAdapter(
            arrayListOf()
        )
        getUser()
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnSendMsg.apply {
            setOnClickListener { sendMsg() }
            text = ""
        }
    }

    private fun sendMsg() {
        val fromId = FirebaseAuth.getInstance().uid
        val toId = safeArgs.id

        val msg = Message(
            binding.edtSendMsg.text.toString(),
            System.currentTimeMillis(),
            fromId,
            toId
        )

        if (binding.edtSendMsg.text?.isEmpty()?.not() == true) {
            fromId?.let {
                FirebaseFirestore.getInstance()
                    .collection("chats")
                    .document(it)
                    .collection(toId)
                    .add(msg)
                    .addOnSuccessListener {
                        Log.d("Enviou", "")
                    }
            }

            fromId?.let {
                FirebaseFirestore.getInstance()
                    .collection("chats")
                    .document(toId)
                    .collection(it)
                    .add(msg)
                    .addOnSuccessListener {

                    }
            }
        }
    }

    private fun getUser() {
        FirebaseFirestore.getInstance()
            .collection("/users")
            .document(FirebaseAuth.getInstance().uid.toString())
            .get()
            .addOnSuccessListener {
                user = it.toObject(User::class.java)
                fetchMsg()
            }
    }

    private fun fetchMsg() {
        if (user != null) {
            val fromId = FirebaseAuth.getInstance().uid
            val toId = safeArgs.id

            fromId?.let {
                FirebaseFirestore.getInstance()
                    .collection("chats")
                    .document(it)
                    .collection(toId)
                    .orderBy("timestamp", Query.Direction.ASCENDING)
                    .addSnapshotListener { value, error ->
                        var list = value?.documentChanges

                        if (list != null) {
                            for (doc in list) {
                                if (doc.type == DocumentChange.Type.ADDED) {
                                    var msg = doc.document.toObject(Message::class.java)
                                    adapterChat?.addItem(msg)
                                }
                            }
                        }
                    }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.rvChat.adapter = adapterChat
    }
}