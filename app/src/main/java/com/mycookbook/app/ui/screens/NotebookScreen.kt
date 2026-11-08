
package com.mycookbook.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mycookbook.app.data.model.NotebookNote

@Composable
fun NotebookScreen(
    notes: List<NotebookNote>,
    textColor: Color,
    card: Color,
    onBack: () -> Unit,
    onSave: (NotebookNote) -> Unit,
    onDelete: (String) -> Unit
) {
    var editing by remember { mutableStateOf<NotebookNote?>(null) }
    var showEditor by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        TextButton(onClick = onBack) { Text("← Назад") }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Мой блокнот 📝",
                color = textColor,
                style = MaterialTheme.typography.headlineSmall
            )
            Button(onClick = {
                editing = null
                showEditor = true
            }) {
                Text("+ Запись")
            }
        }

        Spacer(Modifier.height(12.dp))

        if (notes.isEmpty()) {
            Text("Записей пока нет. Создай первую заметку", color = textColor)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(notes, key = { it.id }) { note ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                editing = note
                                showEditor = true
                            },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = card)
                    ) {
                        Column(Modifier.padding(15.dp)) {
                            Text(note.title, color = textColor,
                                style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(5.dp))
                            Text(note.text, color = textColor)
                            TextButton(onClick = { onDelete(note.id) }) {
                                Text("Удалить")
                            }
                        }
                    }
                }
            }
        }
    }

    if (showEditor) {
        var title by remember(editing?.id) {
            mutableStateOf(editing?.title ?: "")
        }
        var body by remember(editing?.id) {
            mutableStateOf(editing?.text ?: "")
        }

        AlertDialog(
            onDismissRequest = { showEditor = false },
            title = {
                Text(if (editing == null) "Новая запись" else "Изменить запись")
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Заголовок") },
                        singleLine = true
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = body,
                        onValueChange = { body = it },
                        label = { Text("Текст заметки") },
                        minLines = 4
                    )
                }
            },
            confirmButton = {
                TextButton(
                    enabled = title.isNotBlank(),
                    onClick = {
                        val old = editing
                        onSave(
                            NotebookNote(
                                id = old?.id ?: java.util.UUID.randomUUID().toString(),
                                title = title.trim(),
                                text = body.trim(),
                                photos = old?.photos ?: emptyList(),
                                videos = old?.videos ?: emptyList(),
                                createdAt = old?.createdAt ?: System.currentTimeMillis(),
                                updatedAt = System.currentTimeMillis()
                            )
                        )
                        showEditor = false
                    }
                ) {
                    Text("Сохранить")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditor = false }) {
                    Text("Отмена")
                }
            }
        )
    }
}
