package com.pbaileyapps.quickglanceinventory.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ItemsDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(item: Item)
    @Update()
    fun update(item: Item)
    @Query("Select * From Items Where user isnull ORDER BY id ASC")
    fun getAllItems(): LiveData<List<Item>>
    @Query("Select * From Items WHERE :user Like user OR user IsNull ORDER BY id ASC")
    fun getAllItemsForUser(user:String): LiveData<List<Item>>
    @Delete()
    fun deleteItem(item: Item)
    @Query("Delete From Items")
    fun deleteAllItems()
    @Query("Select * From Items WHERE name LIKE :query")
    fun search(query:String): LiveData<List<Item>>
}