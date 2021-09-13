package com.pbaileyapps.shoppingappclone.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class],version=4,exportSchema = false)
abstract class ItemDatabase: RoomDatabase() {
    abstract fun itemDao():ItemsDao
    companion object {
        @Volatile
        private var INSTANCE: ItemDatabase? = null
    fun getDatabase(context: Context): ItemDatabase {
        val tempInstance = INSTANCE
        if (tempInstance != null) {
            return tempInstance
        }
        synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                ItemDatabase::class.java, "items_database"
            ).build()
            INSTANCE = instance
            return instance
        }
    }
}
}