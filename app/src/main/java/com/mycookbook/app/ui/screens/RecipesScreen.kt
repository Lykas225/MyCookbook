
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
import com.mycookbook.app.data.model.Recipe

@Composable
fun RecipesScreen(
    recipes: List<Recipe>,
    favoritesOnly: Boolean,
    textColor: Color,
    card: Color,
    accent: Color,
    onBack: () -> Unit,
    onAdd: () -> Unit,
    onOpen: (Recipe) -> Unit,
    onFavorite: (String) -> Unit
) {
    var search by remember { mutableStateOf("") }

    val filtered = recipes.filter {
        (!favoritesOnly || it.isFavorite) &&
        (it.title.contains(search, true) ||
         it.ingredients.any { ingredient -> ingredient.contains(search, true) })
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = onBack) { Text("← Назад") }
            Button(onClick = onAdd) { Text("+ Рецепт") }
        }

        Text(
            if (favoritesOnly) "Избранное ❤️" else "Мои рецепты 📖",
            color = textColor,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = search,
            onValueChange = { search = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Поиск рецепта или ингредиента") },
            singleLine = true
        )

        Spacer(Modifier.height(12.dp))

        if (filtered.isEmpty()) {
            Text(
                if (favoritesOnly) "В избранном пока пусто"
                else "Рецептов пока нет. Добавь первый рецепт",
                color = textColor
            )
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(filtered, key = { it.id }) { recipe ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onOpen(recipe) },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = card)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(Modifier.weight(1f)) {
                                Text(
                                    recipe.title,
                                    color = textColor,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(recipe.category, color = accent)
                                Text(
                                    "Ингредиентов: ${recipe.ingredients.size} · Шагов: ${recipe.steps.size}",
                                    color = textColor
                                )
                            }

                            TextButton(
                                onClick = { onFavorite(recipe.id) }
                            ) {
                                Text(if (recipe.isFavorite) "❤️" else "♡")
                            }
                        }
                    }
                }
            }
        }
    }
}
