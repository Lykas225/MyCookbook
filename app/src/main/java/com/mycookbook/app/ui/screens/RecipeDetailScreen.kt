
package com.mycookbook.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mycookbook.app.data.model.Recipe

@Composable
fun RecipeDetailScreen(
    recipe: Recipe,
    textColor: Color,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onFavorite: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        TextButton(onClick = onBack) { Text("← Назад") }

        Text(
            recipe.title,
            color = textColor,
            style = MaterialTheme.typography.headlineMedium
        )

        Text(recipe.category, color = textColor)

        Spacer(Modifier.height(12.dp))

        Text(
            recipe.description,
            color = textColor
        )

        Spacer(Modifier.height(20.dp))

        Text(
            "Ингредиенты",
            color = textColor,
            style = MaterialTheme.typography.titleLarge
        )

        recipe.ingredients.forEachIndexed { index, ingredient ->
            Text("${index + 1}. $ingredient", color = textColor)
        }

        Spacer(Modifier.height(20.dp))

        Text(
            "Приготовление",
            color = textColor,
            style = MaterialTheme.typography.titleLarge
        )

        recipe.steps.forEach { step ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            ) {
                Column(Modifier.padding(12.dp)) {
                    Text(
                        "${step.number}. ${step.title}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(step.description)
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = onFavorite,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (recipe.isFavorite) "Убрать из избранного" else "Добавить в избранное")
        }

        OutlinedButton(
            onClick = onEdit,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Изменить рецепт")
        }

        TextButton(
            onClick = onDelete,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Удалить рецепт")
        }
    }
}
