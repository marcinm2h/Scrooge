package mmoch.scrooge

import android.content.res.Resources
import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Matcher
import java.util.regex.Pattern


fun formatSummary(sum: Double, res: Resources): String {
    return res.getString(R.string.summary, sum)
}

class DecimalDigitsInputFilter(precision: Int) :
    InputFilter {
    val mPattern =
        Pattern.compile("[0-9]+((\\.[0-9]{0," + (precision - 1) + "})?)||(\\.)?")
    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val matcher: Matcher = mPattern.matcher(dest)
        return if (!matcher.matches()) "" else null
    }
}
