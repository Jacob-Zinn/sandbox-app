package com.creators.sandbox.ui

import com.creators.sandbox.util.Response
import com.creators.sandbox.util.MessageCallback

interface UICommunicationListener {

    fun onResponseReceived(
        response: Response,
        messageCallback: MessageCallback
    )

    fun expandAppBar()

    fun hideSoftKeyboard()

    fun isStoragePermissionGranted(): Boolean
}