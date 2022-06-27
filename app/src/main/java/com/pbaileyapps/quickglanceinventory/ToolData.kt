package com.pbaileyapps.quickglanceinventory

import android.graphics.drawable.Drawable

class ToolData(name:String,drawable: Drawable) {
    var mName = name
    var mDrawable = drawable
    fun getName():String{
        return mName
    }
    fun getDrawable():Drawable{
        return mDrawable
    }
}