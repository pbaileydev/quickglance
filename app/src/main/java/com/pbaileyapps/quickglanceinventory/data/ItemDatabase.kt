package com.pbaileyapps.quickglanceinventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Item::class],version=5,exportSchema = true)
abstract class ItemDatabase: RoomDatabase() {
    abstract fun itemDao():ItemsDao
    companion object {
        @Volatile
        private var INSTANCE: ItemDatabase? = null
        val migration_4_5: Migration = object: Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Items ADD COLUMN user TEXT DEFAULT ''")
            }
        }
    fun getDatabase(context: Context): ItemDatabase {
        val tempInstance = INSTANCE
        if (tempInstance != null) {
            return tempInstance
        }
        synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                ItemDatabase::class.java, "items_database"
            ).addMigrations(migration_4_5).build()
            INSTANCE = instance
            return instance
        }
    }
}
}
// Database class after the version update.
