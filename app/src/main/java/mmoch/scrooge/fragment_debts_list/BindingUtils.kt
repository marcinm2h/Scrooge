package mmoch.scrooge.fragment_debts_list

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import mmoch.scrooge.database.Debt
import mmoch.scrooge.formatSummary

@BindingAdapter("debtAmount")
fun TextView.setDebtAmount(item: Debt?) {
    item?.let {
        text = item.amount.toString()
    }
}

@BindingAdapter("summary")
fun TextView.setSummary(sum: LiveData<Double>) {
    sum.value?.let {
        text = formatSummary(it, context.resources)
    }
}