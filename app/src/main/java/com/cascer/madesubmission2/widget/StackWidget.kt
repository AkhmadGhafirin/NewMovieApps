package com.cascer.madesubmission2.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import android.widget.Toast
import com.cascer.madesubmission2.R
import com.cascer.madesubmission2.utils.EXTRA_ITEM
import com.cascer.madesubmission2.utils.TOAST_ACTION


class StackWidget : AppWidgetProvider() {

    private fun updateWidget(
        context: Context?, appWidgetManager: AppWidgetManager?, appWidgetId: Int
    ) {
        val intent = Intent(context, StackWidgetService::class.java).apply {
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            data = Uri.parse(this.toUri(Intent.URI_INTENT_SCHEME))
        }

        val toastIntent = Intent(context, StackWidget::class.java).apply {
            action = TOAST_ACTION
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        }

        val toastPendingIntent =
            PendingIntent.getBroadcast(
                context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )

        val views = RemoteViews(context?.packageName, R.layout.stack_widget_layout).apply {
            setRemoteAdapter(R.id.stack_view, intent)
            setEmptyView(R.id.stack_view, R.id.empty_view)
            setPendingIntentTemplate(R.id.stack_view, toastPendingIntent)
        }

        appWidgetManager?.updateAppWidget(appWidgetId, views)
    }

    override fun onUpdate(
        context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        appWidgetIds?.forEach { updateWidget(context, appWidgetManager, it) }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent?.action != null) {
            if (intent.action.equals(TOAST_ACTION)) {
                val title = intent.getStringExtra(EXTRA_ITEM) ?: "Image Banner"
                Toast.makeText(context, title, Toast.LENGTH_SHORT).show()
            }
        }
    }

}