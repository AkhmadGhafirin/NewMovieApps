package com.cascer.favoritemovieapp.utils

import android.content.Context
import android.view.Gravity
import com.valdesekamdem.library.mdtoast.MDToast

class PopUpMessage {

    fun popUpToast(
        context: Context,
        message: String,
        duration: Int = MDToast.LENGTH_SHORT,
        type: Int = MDToast.TYPE_SUCCESS,
        gravity: Int = Gravity.TOP,
        xOffset: Int = 0,
        yOffset: Int = 250
    ) {
        val mdToast = MDToast.makeText(context, message, duration, type)
        mdToast.setGravity(gravity, xOffset, yOffset)
        mdToast.show()
    }
}