package com.pbaileyapps.shoppingappclone.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class ItemRepository(private val itemsDao: ItemsDao) {
    val readAllData: LiveData<List<Item>> = itemsDao.getAllItems()

    suspend fun addItem(item:Item){
        itemsDao.insert(item)
    }
    suspend fun updateItem(item:Item){
        itemsDao.update(item)
    }
    suspend fun deleteItem(item:Item){
        itemsDao.deleteItem(item)
    }
    suspend fun deleteAllItems(){
        itemsDao.deleteAllItems()
    }
    fun search(query:String): LiveData<List<Item>> {
        return itemsDao.search(query)
    }

}