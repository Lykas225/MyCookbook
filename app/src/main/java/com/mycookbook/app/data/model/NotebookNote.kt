
package com.mycookbook.app.data.model

data class NotebookNote(
    val id: String = java.util.UUID.randomUUID().toString(),
    val title: String = "",
    val text: String = "",
    val photos: List<MediaAttachment> = emptyList(),
    val videos: List<MediaAttachment> = emptyList(),
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
