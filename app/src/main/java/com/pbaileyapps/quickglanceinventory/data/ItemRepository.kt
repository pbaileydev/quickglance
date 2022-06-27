package com.pbaileyapps.quickglanceinventory.data

import androidx.lifecycle.LiveData

class ItemRepository(private val itemsDao: ItemsDao) {
    val readAllData: LiveData<List<Item>> = itemsDao.getAllItems()
    suspend fun addItem(item:Item){
        var mItem = item
        if(item.amount < 0 && item.needed >= 0) {

            Item(item.id, item.name, item.drawable, 0, item.needed, item.sku, item.user)
        }
        if(item.needed < 0 && item.amount < 0){
            mItem = Item(item.id,item.name,item.drawable,0,0,item.sku,item.user)
        }
        if(item.needed <0 && item.amount>= 0){
            mItem = Item(item.id,item.name,item.drawable,item.amount,0,item.sku,item.user)
        }

        itemsDao.insert(mItem)
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
    fun getAllItemsForUser(user:String):LiveData<List<Item>>{
        return itemsDao.getAllItemsForUser(user)
    }
    fun search(query:String): LiveData<List<Item>> {
        return itemsDao.search(query)
    }

}