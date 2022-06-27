package com.pbaileyapps.quickglanceinventory

import com.pbaileyapps.quickglanceinventory.data.Item
import com.pbaileyapps.quickglanceinventory.data.ItemViewModel
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    val item:Item = Item(0,"Pencils",null,-5,-7,null,null)
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun testItemAmountAcceptsOnlyNonNegativeIntegers(){
        assertTrue("Number is set to a negative", item.amount >= 0)
    }
    @Test
    fun testItemNeededAcceptsOnlyNonNegativeIntegers(){
        assertTrue("Number is set to a negative", item.needed >= 0)
    }
}