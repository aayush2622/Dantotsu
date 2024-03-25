package ani.dantotsu.widgets

import android.content.Intent
import android.widget.RemoteViewsService
import ani.dantotsu.util.Logger

class StatsRemoteViewsService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        Logger.log("StatsRemoteViewsFactory onGetViewFactory")
        return StatsRemoteViewsFactory(applicationContext)
    }
}
