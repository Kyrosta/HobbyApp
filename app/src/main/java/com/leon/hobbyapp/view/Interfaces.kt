package com.leon.hobbyapp.view

import android.view.View
import com.leon.hobbyapp.model.User
import java.util.Objects

interface ButtonClickListener{
    fun onButtonClick(v: View)
}

interface ButtonActionNav {
    fun onButtonActionNavClick(v: View)
}

interface ButtonUpdate {
    fun onButtonUpdateClick(v:View, obj: User)
}