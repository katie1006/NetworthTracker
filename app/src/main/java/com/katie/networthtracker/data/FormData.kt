package com.katie.networthtracker.data

interface FormData {
    val name: String
}

/**
 * @param positionInType There might be some special entries that share the same path. In this case,
 * positionInType would be useful as unique identifier.
 */
data class FormDataWrapper(val data: FormData, val path: Array<String>, val positionInType: Int = 0) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FormDataWrapper

        if (!path.contentEquals(other.path)) return false

        return true
    }

    override fun hashCode(): Int {
        return path.contentHashCode()
    }
}

data class FormHeader(override val name: String, val currency: String) : FormData