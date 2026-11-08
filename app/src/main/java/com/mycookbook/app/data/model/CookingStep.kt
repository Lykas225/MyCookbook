
package com.mycookbook.app.data.model

data class CookingStep(
    val id: String = java.util.UUID.randomUUID().toString(),
    val number: Int = 1,
    val title: String = "",
    val description: String = "",
    val photos: List<MediaAttachment> = emptyList(),
    val videos: List<MediaAttachment> = emptyList()
)
