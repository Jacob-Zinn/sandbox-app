package com.creators.sandbox.ui

import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment()
{
    lateinit var uiCommunicationListener: UICommunicationListener

}