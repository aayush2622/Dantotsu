package ani.dantotsu.widgets

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import ani.dantotsu.R
import ani.dantotsu.getAppString
import ani.dantotsu.util.Logger
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class StatsRemoteViewsFactory(private val context: Context) :
    RemoteViewsService.RemoteViewsFactory {
    private var widgetItems = mutableListOf<StatsWidgetItem>()

    override fun onCreate() {
        // 4 items for testing
        widgetItems.clear()
        Logger.log("StatsRemoteViewsFactory onCreate")
        widgetItems = List(1) {
            StatsWidgetItem(
                getAppString(R.string.loading), getAppString(R.string.loading)
            )
        }.toMutableList()
    }

    override fun onDataSetChanged() {
        // 4 items for testing
        Logger.log("StatsRemoteViewsFactory onDataSetChanged")
        widgetItems.clear()
        widgetItems.add(
            StatsWidgetItem(
                getAppString(R.string.loading), getAppString(R.string.loading)
            )
        )
    }

    override fun onDestroy() {
        widgetItems.clear()
    }

    override fun getCount(): Int {
        return widgetItems.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        Logger.log("StatsRemoteViewsFactory getViewAt")
        val item = widgetItems[position]
        val rv = RemoteViews(context.packageName, R.layout.widget_stats).apply {
            setTextViewText(R.id.animeWatched, item.readCount)
            setTextViewText(R.id.mangaRead, item.watchedCount)
        }

        return rv
    }

    override fun getLoadingView(): RemoteViews {
        return RemoteViews(context.packageName, R.layout.widget_stats)
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }
}

data class StatsWidgetItem(val watchedCount: String, val readCount: String)