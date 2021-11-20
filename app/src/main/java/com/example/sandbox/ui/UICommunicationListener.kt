package com.example.sandbox.ui

import com.example.sandbox.util.Response
import com.example.sandbox.util.MessageCallback

interface UICommunicationListener {

    fun onResponseReceived(
        response: Response,
        messageCallback: MessageCallback
    )

    fun expandAppBar()

    fun hideSoftKeyboard()

    fun isStoragePermissionGranted(): Boolean
}