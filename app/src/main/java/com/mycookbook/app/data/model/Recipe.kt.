
package com.mycookbook.app.data.model

data class Recipe(
    val id: String = java.util.UUID.randomUUID().toString(),
    val title: String = "",
    val description: String = "",
    val category: String = "Другое",
    val ingredients: List<String> = emptyList(),
    val steps: List<CookingStep> = emptyList(),
    val photos: List<MediaAttachment> = emptyList(),
    val videos: List<MediaAttachment> = emptyList(),
    val isFavorite: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
