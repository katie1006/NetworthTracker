package com.katie.networthtracker.data

data class EntryGroup(override val name: String, val subgroups: List<EntryGroup>? = null, val entries: List<Entry>? = null) : FormData

/**
 * This class exists to avoid keeping multiple copies of EntryGroup in RecyclerView.Adapter
 */
data class EntryGroupPlaceholder(override val name: String) : FormData