package ani.dantotsu.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.widget.RemoteViews
import androidx.core.content.res.ResourcesCompat
import ani.dantotsu.R
import ani.dantotsu.connections.anilist.Anilist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tachiyomi.core.util.lang.launchIO

/**
 * Implementation of App Widget functionality.
 */
class StatsWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        appWidgetIds.forEach { appWidgetId ->
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        // When the user deletes the widget, delete the preference associated with it.
        for (appWidgetId in appWidgetIds) {
            deleteTitlePref(context, appWidgetId)
        }
        super.onDeleted(context, appWidgetIds)
    }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
    }

    companion object {
        fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {

            val views = RemoteViews(context.packageName, R.layout.widget_stats)
            launchIO {
                val respond = if (Anilist.userid != null && Anilist.userid != -1)
                    Anilist.query.getUserProfile(Anilist.userid!!)
                else if (Anilist.username != null)
                    Anilist.query.getUserProfile(Anilist.username!!) else null
                respond?.data?.user?.let { user ->
                    withContext(Dispatchers.Main) {
                        views.setTextViewText(R.id.animeWatched,
                            user.statistics.anime.count.toString()
                        )
                        views.setTextViewText(R.id.mangaRead, user.statistics.manga.count.toString())
                        views.setTextViewText(R.id.episodesWatched, user.statistics.anime.episodesWatched.toString())
                        views.setTextViewText(R.id.chaptersRead, user.statistics.manga.chaptersRead.toString())

                        // Instruct the widget manager to update the widget
                        appWidgetManager.updateAppWidget(appWidgetId, views)
                    }
                }
            }
        }
    }
}