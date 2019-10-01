package com.katie.networthtracker.data

data class EntryGroup(val name: String, val subgroups: List<EntryGroup>? = null, val entries: List<Entry>? = null)