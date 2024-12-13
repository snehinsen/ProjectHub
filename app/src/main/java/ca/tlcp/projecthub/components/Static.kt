package ca.tlcp.projecthub.components

import android.content.Context
import android.content.SharedPreferences

class Static {

    companion object {

        private var appContext: Context? = null
        private var prefs: SharedPreferences? = null
        fun getAppContext(): Context? {
            return appContext
        }

        fun setAppContext(context: Context) {
            appContext = context
        }
        fun getSharedPreferences(): SharedPreferences? {
            return prefs
        }
        fun setSharedPreferences(sharedPrefs: SharedPreferences) {
            prefs = sharedPrefs
        }
    }
}
