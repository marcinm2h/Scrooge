package mmoch.scrooge

import android.content.res.Resources
import android.text.InputFilter
import android.text.Spanned

fun formatSummary(sum: Double, res: Resources): String {
    return res.getString(R.string.summary, sum)
}

class DecimalDigitsInputFilter(private val precision: Int) :
    InputFilter {
    private val pattern = "[0-9]+((\\.[0-9]{0,${precision - 1}})?)||(\\.)?"
        .toRegex()

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        return if (!pattern.matches(dest)) "" else null
    }
}
