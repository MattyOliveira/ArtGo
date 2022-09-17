package br.com.t4.artgoproject.ui.onboarding

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import br.com.t4.artgoproject.databinding.ItemTypeBinding

class TypeAdapter(
    private val list: List<TypeModel>,
    private var itemClick: ((Int) -> Unit)? = null
) : RecyclerView.Adapter<TypeAdapter.TypeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewHolder {
        val binding = ItemTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TypeViewHolder(binding)

    }

    override fun onBindViewHolder(holder: TypeViewHolder, position: Int) = holder.bind(position)

    override fun getItemCount() = list.size

    inner class TypeViewHolder(val binding: ItemTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            with(binding) {
                typeTitle.text = binding.root.context.getText(list[position].title)
                typeSubTitle.text = binding.root.context.getText(list[position].subTitle)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    typeImage.setImageDrawable(binding.root.context.getDrawable(list[position].image))
                }
                typeContainer.setOnClickListener {
                    itemClick?.invoke(position)
                }
            }
        }
    }
}

data class TypeModel(
    @StringRes val title: Int,
    @StringRes val subTitle: Int,
    @DrawableRes val image: Int
)