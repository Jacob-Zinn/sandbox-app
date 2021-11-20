package com.example.sandbox.util


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.parcelize.IgnoredOnParcel
import timber.log.Timber

class MessageStack: ArrayList<Message>() {


    @IgnoredOnParcel
    private val _Message: MutableLiveData<Message?> = MutableLiveData()

    @IgnoredOnParcel
    val message: LiveData<Message?>
        get() = _Message

    override fun addAll(elements: Collection<Message>): Boolean {
        for(element in elements){
            add(element)
        }
        return true // always return true. We don't care about result bool.
    }

    override fun add(element: Message): Boolean {
        if(this.contains(element)){ // prevent duplicate errors added to stack
            return false
        }
        val transaction = super.add(element)
        if(this.size == 1){
            setStateMessage(message = element)
        }
        return transaction
    }

    override fun removeAt(index: Int): Message {
        try{
            val transaction = super.removeAt(index)
            if(this.size > 0){
                setStateMessage(message = this[0])
            }
            else{
                Timber.d( "stack is empty: ")
                setStateMessage(null)
            }
            return transaction
        }catch (e: IndexOutOfBoundsException){
            e.printStackTrace()
        }
        return Message(
            Response(
                message = "does nothing",
                uiComponentType = UIComponentType.None,
                messageType = MessageType.None
            )
        ) // this does nothing
    }

    private fun setStateMessage(message: Message?){
        _Message.value = message
    }
}


























