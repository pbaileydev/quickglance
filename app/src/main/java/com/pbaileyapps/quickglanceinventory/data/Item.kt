package com.pbaileyapps.quickglanceinventory.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Items")
class Item(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name:String,
    val drawable:String?,
    val amount:Int,
    val needed:Int,
    val sku:String?,
    val user:String?
) : Parcelable{
}