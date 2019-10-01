package com.katie.networthtracker

import com.katie.networthtracker.data.Entry
import com.katie.networthtracker.data.EntryGroup
import com.katie.networthtracker.network.NetworthAPI
import org.junit.Assert.*
import org.junit.Test

class NetworkApiTest {

    @Test
    fun testCalculateEmptyInput() {
        // create an api object
        val api = NetworthAPI()
        val result1 = api.calculate(EntryGroup("Assets"), EntryGroup("Liabilities"), "CAD")
        assertEquals(0, result1.net)
        assertEquals(0, result1.totalAssets)
        assertEquals(0, result1.totalLiabilities)

        val result2 = api.calculate(EntryGroup("Assets", null, arrayListOf(Entry(""))), EntryGroup("Liabilities", arrayListOf()), "CAD")
        assertEquals(0, result2.net)
        assertEquals(0, result2.totalAssets)
        assertEquals(0, result2.totalLiabilities)
    }

    @Test
    fun testCalculateNonEmptyInput() {
        // create an api object
        val api = NetworthAPI()
        val result1 = api.calculate(
            EntryGroup("Assets", arrayListOf(
                EntryGroup("Sub1", null, arrayListOf(Entry(""), Entry("", 2))),
                EntryGroup("Sub2", null, arrayListOf(Entry("", 3)))
            )),
            EntryGroup("Liabilities"),
            "CAD"
        )
        assertEquals(5, result1.net)
        assertEquals(5, result1.totalAssets)
        assertEquals(0, result1.totalLiabilities)

        val result2 = api.calculate(
            EntryGroup("Assets", arrayListOf(
                EntryGroup("", null, arrayListOf(Entry(""), Entry("", 2)))
            )),
            EntryGroup("Liabilities", arrayListOf(
                EntryGroup("", null, arrayListOf(Entry("", 3)))
            )),
            "CAD"
        )
        assertEquals(-1, result2.net)
        assertEquals(2, result2.totalAssets)
        assertEquals(3, result2.totalLiabilities)
    }
}