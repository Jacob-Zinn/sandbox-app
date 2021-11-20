package com.creators.sandbox.models

data class Queries(
    var quality1: String? = null,
    var quality2: String? = null,
    var quality3: String? = null,
    var expertise1: String? = null,
    var expertise2: String? = null,
    var expertise3: String? = null,
) {

    fun clear() {
        quality1 = null
        quality2 = null
        quality3 = null
        expertise1 = null
        expertise2 = null
        expertise3 = null
    }
}
