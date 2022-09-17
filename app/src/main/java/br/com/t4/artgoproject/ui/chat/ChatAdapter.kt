package br.com.t4.artgoproject.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.t4.artgoproject.R
import br.com.t4.artgoproject.databinding.ItemChatLeftBinding
import br.com.t4.artgoproject.databinding.ItemChatRightBinding
import br.com.t4.artgoproject.ui.chat.Const.FROM_MESSAGE
import br.com.t4.artgoproject.ui.chat.Const.TO_MESSAGE
import com.google.firebase.auth.FirebaseAuth

class ChatAdapter(
    private val list: ArrayList<Message>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun addItem(msg: Message) {
        this.list.add(msg)
        notifyDataSetChanged()
    }

    inner class ArtistViewHolder(
        private val binding: ItemChatRightBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Message) {
            binding.apply {
                txtChatRight.text = item.mensage
                imgChatRight.setImageDrawable(binding.root.context.getDrawable(R.drawable.ic_guitar))
            }
        }
    }

    inner class ContractViewHolder(
        private val binding: ItemChatLeftBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Message) {
            binding.apply {
                txtChatLeft.text = item.mensage
                imgChatLeft.setImageDrawable(binding.root.context.getDrawable(R.drawable.ic_money))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        if (viewType == FROM_MESSAGE) {
            ContractViewHolder(
                ItemChatLeftBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            ArtistViewHolder(
                ItemChatRightBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        if (FROM_MESSAGE == getItemViewType(position)) {
            (holder as ContractViewHolder).bind(list[position])
        } else {
            (holder as ArtistViewHolder).bind(list[position])
        }

    override fun getItemCount() = list.size

    override fun getItemViewType(position: Int): Int {
        return if (list[position].fromId == FirebaseAuth.getInstance().uid) TO_MESSAGE else FROM_MESSAGE
    }
}

private object Const {
    const val FROM_MESSAGE = 0
    const val TO_MESSAGE = 1
}

data class Message(
    var mensage: String? = null,
    var timestamp: Long = 0,
    var fromId: String? = null,
    var toId: String? = null,
)
