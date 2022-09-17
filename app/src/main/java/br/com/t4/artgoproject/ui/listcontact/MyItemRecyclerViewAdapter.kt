package br.com.t4.artgoproject.ui.listcontact

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.t4.artgoproject.databinding.FragmentListContactBinding
import br.com.t4.artgoproject.model.User

class MyItemRecyclerViewAdapter(
    private val itemClick: ((String) -> Unit)?
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    private val list: MutableList<User> = mutableListOf()
    fun addData(user: User) {
        this.list.add(user)
        notifyDataSetChanged()
    }

    fun clear() {
        list.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        FragmentListContactBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(list[position])

    override fun getItemCount() = list.size

    inner class ViewHolder(private val binding: FragmentListContactBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: User) {
            binding.apply {
                txtName.text = item.userName
                txtGender.text = item.generoMusical
            }

            binding.root.setOnClickListener {
                item.id?.let { it1 -> itemClick?.invoke(it1) }
            }
        }
    }
}
