package com.cascer.madesubmission2.widget

import android.content.Intent
import android.util.Log
import android.widget.RemoteViewsService
import org.koin.android.ext.android.inject

class StackWidgetService : RemoteViewsService() {

    private val stackWidgetRemoteViewsFactory: StackWidgetRemoteViewsFactory by inject()

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        Log.d("ON_GET_VIEW_FACTORY", "CUY BRO")
        return stackWidgetRemoteViewsFactory
    }
}