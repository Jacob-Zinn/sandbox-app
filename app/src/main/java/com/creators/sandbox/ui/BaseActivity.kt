package com.creators.sandbox.ui


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.creators.sandbox.R
import com.creators.sandbox.session.SessionManager
import com.creators.sandbox.util.Constants.Companion.PERMISSIONS_REQUEST_READ_STORAGE
import com.creators.sandbox.util.MessageType
import com.creators.sandbox.util.Response
import com.creators.sandbox.util.MessageCallback
import com.creators.sandbox.util.UIComponentType
import timber.log.Timber
import javax.inject.Inject

abstract class BaseActivity: AppCompatActivity(),
    UICommunicationListener
{
    private var dialogInView: MaterialDialog? = null

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onResponseReceived(
        response: Response,
        messageCallback: MessageCallback
    ) {

        when(response.uiComponentType){
            is UIComponentType.Dialog -> {
                displayDialog(
                    response = response,
                    messageCallback = messageCallback
                )
            }
            is UIComponentType.None -> {
                // This would be a good place to send to your Error Reporting
                // software of choice (ex: Firebase crash reporting)
                Timber.i("onResponseReceived: ${response.message}")
                messageCallback.removeMessage()
            }
        }
    }

    private fun displayDialog(
        response: Response,
        messageCallback: MessageCallback
    ){
        Timber.i("displayDialog: ")
        response.message?.let { message ->

            dialogInView = when (response.messageType) {

                is MessageType.Error -> {
                    displayErrorDialog(
                        message = message,
                        messageCallback = messageCallback
                    )
                }

                is MessageType.Success -> {
                    displaySuccessDialog(
                        message = message,
                        messageCallback = messageCallback
                    )
                }

                is MessageType.Info -> {
                    displayInfoDialog(
                        message = message,
                        messageCallback = messageCallback
                    )
                }

                else -> {
                    // do nothing
                    messageCallback.removeMessage()
                    null
                }
            }
        }?: messageCallback.removeMessage()
    }

    override fun hideSoftKeyboard() {
        if (currentFocus != null) {
            val inputMethodManager = getSystemService(
                Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager
                .hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

    override fun isStoragePermissionGranted(): Boolean{
        if (
            ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED  ) {


            ActivityCompat.requestPermissions(this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                PERMISSIONS_REQUEST_READ_STORAGE
            )

            return false
        } else {
            // Permission has already been granted
            return true
        }
    }

    override fun onPause() {
        super.onPause()
        if(dialogInView != null){
            (dialogInView as MaterialDialog).dismiss()
            dialogInView = null
        }
    }

    private fun displaySuccessDialog(
        message: String?,
        messageCallback: MessageCallback
    ): MaterialDialog {
        return MaterialDialog(this)
            .show{
                title(R.string.text_success)
                message(text = message)
                positiveButton(R.string.text_ok){
                    messageCallback.removeMessage()
                    dismiss()
                }
                onDismiss {
                    dialogInView = null
                }
                cancelable(false)
            }
    }

    private fun displayErrorDialog(
        message: String?,
        messageCallback: MessageCallback
    ): MaterialDialog {
        return MaterialDialog(this)
            .show{
                title(R.string.text_error)
                message(text = message)
                positiveButton(R.string.text_ok){
                    messageCallback.removeMessage()
                    dismiss()
                }
                onDismiss {
                    dialogInView = null
                }
                cancelable(false)
            }
    }

    private fun displayInfoDialog(
        message: String?,
        messageCallback: MessageCallback
    ): MaterialDialog {
        return MaterialDialog(this)
            .show{
                title(R.string.info)
                message(text = message)
                positiveButton(R.string.text_ok){
                    messageCallback.removeMessage()
                    dismiss()
                }
                onDismiss {
                    dialogInView = null
                }
                cancelable(false)
            }
    }
}









