package mmoch.scrooge

import android.content.res.Resources

fun formatSummary(sum: Double, res: Resources): String {
    return res.getString(R.string.summary, sum)
}