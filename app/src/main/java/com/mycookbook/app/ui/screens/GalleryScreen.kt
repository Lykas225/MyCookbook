
package com.mycookbook.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mycookbook.app.data.model.MediaAttachment

@Composable
fun GalleryScreen(
    media: List<MediaAttachment>,
    videosOnly: Boolean,
    textColor: Color,
    onBack: () -> Unit
) {
    val filtered = media.filter {
        if (videosOnly) it.type == "VIDEO" else it.type == "PHOTO"
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        TextButton(onClick = onBack) { Text("← Назад") }

        Text(
            if (videosOnly) "Видео приготовления 🎬" else "Фото блюд 📷",
            color = textColor,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(14.dp))

        if (filtered.isEmpty()) {
            Text(
                "Здесь пока пусто. Добавленные к рецептам файлы появятся здесь",
                color = textColor
            )
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(filtered, key = { it.id }) { item ->
                    Card(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(12.dp)) {
                            Text(item.name.ifBlank { "Без названия" })
                            if (item.type == "PHOTO") {
                                AsyncImage(
                                    model = item.path,
                                    contentDescription = item.name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(max = 260.dp)
                                )
                            } else {
                                Text("🎬 Видео: ${item.path}")
                            }
                        }
                    }
                }
            }
        }
    }
}
