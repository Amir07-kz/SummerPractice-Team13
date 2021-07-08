package summerpractice.team13.guessthedrawing.utils

import android.content.Context

import android.content.res.Configuration
import java.util.*

class LocaleUtils {
    companion object {
        fun setLocaleLanguage(localCode: String, context: Context) {
            val locale = Locale(localCode)
            Locale.setDefault(locale)

            val config = Configuration()
            config.setLocale(locale)

            context.resources?.updateConfiguration(config, context.resources.displayMetrics)



        }
    }
}