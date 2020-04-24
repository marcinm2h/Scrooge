package mmoch.scrooge.fragment_debts_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mmoch.scrooge.database.Debt
import mmoch.scrooge.databinding.ListItemDebtBinding

class DebtAdapter(private val viewModel: DebtsListViewModel) : ListAdapter<Debt,
        DebtAdapter.ViewHolder>(DebtDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, viewModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ListItemDebtBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Debt, viewModel: DebtsListViewModel) {
            binding.item = item
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemDebtBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class DebtDiffCallback : DiffUtil.ItemCallback<Debt>() {
    override fun areItemsTheSame(oldItem: Debt, newItem: Debt): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Debt, newItem: Debt): Boolean {
        return oldItem == newItem
    }
}
