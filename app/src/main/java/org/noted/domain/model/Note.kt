package org.noted.domain.model

data class Note(
    var content: String = ""
) {
    var id: String? = null

    constructor(id: String, content: String): this(content) {
        this.id = id
    }
}