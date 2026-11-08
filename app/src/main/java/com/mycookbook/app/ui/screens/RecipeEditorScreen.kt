
package com.mycookbook.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mycookbook.app.data.model.CookingStep
import com.mycookbook.app.data.model.Recipe

@Composable
fun RecipeEditorScreen(
    recipe: Recipe?,
    textColor: Color,
    onBack: () -> Unit,
    onSave: (Recipe) -> Unit
) {
    var title by remember(recipe?.id) { mutableStateOf(recipe?.title ?: "") }
    var description by remember(recipe?.id) { mutableStateOf(recipe?.description ?: "") }
    var category by remember(recipe?.id) { mutableStateOf(recipe?.category ?: "Другое") }
    var ingredients by remember(recipe?.id) {
        mutableStateOf(recipe?.ingredients?.joinToString("\n") ?: "")
    }
    var steps by remember(recipe?.id) {
        mutableStateOf(recipe?.steps?.joinToString("\n") { it.description } ?: "")
    }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        TextButton(onClick = onBack) { Text("← Назад") }

        Text(
            if (recipe == null) "Новый рецепт" else "Изменить рецепт",
            color = textColor,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Название блюда") },
            singleLine = true
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Категория") }
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Описание блюда") },
            minLines = 3
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = ingredients,
            onValueChange = { ingredients = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Ингредиенты — каждый с новой строки") },
            minLines = 5
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = steps,
            onValueChange = { steps = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Шаги приготовления — каждый с новой строки") },
            minLines = 5
        )

        Spacer(Modifier.height(18.dp))

        Button(
            onClick = {
                if (title.isNotBlank()) {
                    val stepList = steps.lines()
                        .map { it.trim() }
                        .filter { it.isNotBlank() }
                        .mapIndexed { index, text ->
                            CookingStep(
                                number = index + 1,
                                title = "Шаг ${index + 1}",
                                description = text
                            )
                        }

                    val updated = Recipe(
                        id = recipe?.id ?: java.util.UUID.randomUUID().toString(),
                        title = title.trim(),
                        description = description.trim(),
                        category = category.trim().ifBlank { "Другое" },
                        ingredients = ingredients.lines()
                            .map { it.trim() }
                            .filter { it.isNotBlank() },
                        steps = stepList,
                        photos = recipe?.photos ?: emptyList(),
                        videos = recipe?.videos ?: emptyList(),
                        isFavorite = recipe?.isFavorite ?: false,
                        createdAt = recipe?.createdAt ?: System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis()
                    )

                    onSave(updated)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = title.isNotBlank()
        ) {
            Text("Сохранить рецепт")
        }
    }
}
