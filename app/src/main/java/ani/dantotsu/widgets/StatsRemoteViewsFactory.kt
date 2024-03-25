package ani.dantotsu.widgets

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import ani.dantotsu.R
import ani.dantotsu.connections.anilist.Anilist
import ani.dantotsu.getAppString
import ani.dantotsu.util.Logger
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class StatsRemoteViewsFactory(private val context: Context) :
    RemoteViewsService.RemoteViewsFactory {

    override fun onCreate() { }

    override fun onDataSetChanged() { }

    override fun onDestroy() { }

    override fun getCount(): Int {
        return 1
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.widget_stats).apply {
            setTextViewText(R.id.animeWatched, Anilist.episodesWatched.toString())
            setTextViewText(R.id.mangaRead, Anilist.chapterRead.toString())
            setTextViewText(R.id.episodesWatched, Anilist.episodesWatched.toString())
            setTextViewText(R.id.chaptersRead, Anilist.chapterRead.toString())
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