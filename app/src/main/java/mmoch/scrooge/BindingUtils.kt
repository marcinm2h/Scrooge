package mmoch.scrooge

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mmoch.scrooge.database.Debt
import mmoch.scrooge.formatSummary

@BindingAdapter("debtAmount")
fun TextView.setDebtAmount(item: Debt?) {
    item?.let {
        text = "%.2f".format(item.amount)
    }
}

@BindingAdapter("summary")
fun TextView.setSummary(sum: LiveData<Double>) {
    sum.value?.let {
        text = formatSummary(it, context.resources)
    }
}

// https://developer.android.com/topic/libraries/data-binding/index.html
@BindingAdapter("goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}
