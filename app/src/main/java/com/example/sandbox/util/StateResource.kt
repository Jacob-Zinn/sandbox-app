package com.example.sandbox.util

data class Message(val response: Response)

data class Response(
    var message: String?,
    val uiComponentType: UIComponentType,
    val messageType: MessageType
)

sealed class UIComponentType{

    object Dialog : UIComponentType()

    object None: UIComponentType()
}

sealed class MessageType{

    object Success: MessageType()

    object Error: MessageType()

    object Info: MessageType()

    object None: MessageType()
}


interface MessageCallback{
    fun removeMessage()
}
