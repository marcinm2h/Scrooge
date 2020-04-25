package mmoch.scrooge

import android.content.res.Resources
import android.text.InputFilter
import android.text.Spanned

fun formatSummary(sum: Double, res: Resources): String {
    return res.getString(R.string.summary, sum)
}

fun stringToDouble(string: String?): Double {
    if (string == null) {
        return 0.0
    }
    return string.replace(",", ".").toDouble()
}

fun doubleToString(double: Double?, precision: Int = 2): String {
    if (double == null) {
        return "0.0"
    }
    return "%.${precision}f".format(double).replace(",", ".")
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
