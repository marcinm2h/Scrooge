package mmoch.scrooge.fragment_debts_list

import android.widget.TextView
import androidx.databinding.BindingAdapter
import mmoch.scrooge.database.Debt


@BindingAdapter("debtorName")
fun TextView.setDebtorName(item: Debt?) {
    item?.let {
        text = item.debtorName
    }
}

@BindingAdapter("debtAmount")
fun TextView.setDebtAmount(item: Debt?) {
    item?.let {
        text = item.amount.toString()
    }
}
