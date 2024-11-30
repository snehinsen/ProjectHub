package ca.tlcp.projecthub.components

import android.content.Context

class Static {

    companion object {
        private var appContext: Context? = null

        fun getAppContext(): Context? {
            return appContext
        }

        fun setAppContext(context: Context) {
            appContext = context
        }
    }
}
