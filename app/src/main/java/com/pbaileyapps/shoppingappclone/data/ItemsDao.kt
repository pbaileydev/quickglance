package com.pbaileyapps.shoppingappclone.data

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.concurrent.Flow

@Dao
interface ItemsDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(item: Item)
    @Update()
    fun update(item: Item)
    @Query("Select * From Items ORDER BY id ASC")
    fun getAllItems(): LiveData<List<Item>>
    @Delete()
    fun deleteItem(item: Item)
    @Query("Delete From Items")
    fun deleteAllItems()
    @Query("Select * From Items WHERE name LIKE :query")
    fun search(query:String): LiveData<List<Item>>
}