
package com.mycookbook.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mycookbook.app.data.model.Recipe
import com.mycookbook.app.data.repository.CookbookRepository
import com.mycookbook.app.ui.screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CookbookApp()
        }
    }
}

@Composable
fun CookbookApp() {
    val context = LocalContext.current

    val prefs = remember {
        context.getSharedPreferences("cookbook_settings", 0)
    }

    val repository = remember {
        CookbookRepository(context.applicationContext)
    }

    var premium by remember {
        mutableStateOf(prefs.getBoolean("premium", true))
    }

    var screen by remember { mutableStateOf("home") }
    var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }
    var editingRecipe by remember { mutableStateOf<Recipe?>(null) }

    var recipes by remember {
        mutableStateOf(repository.getRecipes())
    }

    var notes by remember {
        mutableStateOf(repository.getNotes())
    }

    fun refreshData() {
        recipes = repository.getRecipes()
        notes = repository.getNotes()
    }

    val background = if (premium) Color(0xFF171717)
        else Color(0xFFFFF8ED)

    val card = if (premium) Color(0xFF292929)
        else Color.White

    val textColor = if (premium) Color(0xFFFFF5E5)
        else Color(0xFF493527)

    val accent = if (premium) Color(0xFFD6AD60)
        else Color(0xFF98633E)

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = background
        ) {
            when (screen) {

                "home" -> {
                    HomeScreen(
                        premium = premium,
                        background = background,
                        card = card,
                        textColor = textColor,
                        accent = accent,
                        onPremiumChange = { value ->
                            premium = value
                            prefs.edit()
                                .putBoolean("premium", value)
                                .apply()
                        },
                        onNavigate = { destination ->
                            screen = destination
                        }
                    )
                }

                "recipes", "favorites" -> {
                    RecipesScreen(
                        recipes = recipes,
                        favoritesOnly = screen == "favorites",
                        textColor = textColor,
                        card = card,
                        accent = accent,
                        onBack = { screen = "home" },
                        onAdd = {
                            editingRecipe = null
                            screen = "editor"
                        },
                        onOpen = { recipe ->
                            selectedRecipe = recipe
                            screen = "detail"
                        },
                        onFavorite = { id ->
                            repository.toggleFavorite(id)
                            refreshData()
                        }
                    )
                }

                "editor" -> {
                    RecipeEditorScreen(
                        recipe = editingRecipe,
                        textColor = textColor,
                        onBack = {
                            screen = if (editingRecipe == null) {
                                "recipes"
                            } else {
                                "detail"
                            }
                        },
                        onSave = { recipe ->
                            if (recipes.any { it.id == recipe.id }) {
                                repository.updateRecipe(recipe)
                            } else {
                                repository.addRecipe(recipe)
                            }

                            refreshData()
                            selectedRecipe = recipe
                            screen = "detail"
                        }
                    )
                }

                "detail" -> {
                    val recipe = recipes.find {
                        it.id == selectedRecipe?.id
                    }

                    if (recipe != null) {
                        RecipeDetailScreen(
                            recipe = recipe,
                            textColor = textColor,
                            onBack = { screen = "recipes" },
                            onEdit = {
                                editingRecipe = recipe
                                screen = "editor"
                            },
                            onDelete = {
                                repository.deleteRecipe(recipe.id)
                                refreshData()
                                selectedRecipe = null
                                screen = "recipes"
                            },
                            onFavorite = {
                                repository.toggleFavorite(recipe.id)
                                refreshData()
                            }
                        )
                    } else {
                        Column(
                            Modifier.fillMaxSize().padding(20.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Рецепт не найден", color = textColor)
                            Button(onClick = { screen = "recipes" }) {
                                Text("К рецептам")
                            }
                        }
                    }
                }

                "notebook" -> {
                    NotebookScreen(
                        notes = notes,
                        textColor = textColor,
                        card = card,
                        onBack = { screen = "home" },
                        onSave = { note ->
                            if (notes.any { it.id == note.id }) {
                                repository.updateNote(note)
                            } else {
                                repository.addNote(note)
                            }
                            refreshData()
                        },
                        onDelete = { id ->
                            repository.deleteNote(id)
                            refreshData()
                        }
                    )
                }

                "photos", "videos" -> {
                    val media = if (screen == "photos") {
                        recipes.flatMap { it.photos } +
                            notes.flatMap { it.photos }
                    } else {
                        recipes.flatMap { it.videos } +
                            notes.flatMap { it.videos }
                    }

                    GalleryScreen(
                        media = media,
                        videosOnly = screen == "videos",
                        textColor = textColor,
                        onBack = { screen = "home" }
                    )
                }

                "assistant" -> {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "ИИ-помощник 🤖",
                            color = textColor,
                            style = MaterialTheme.typography.headlineSmall
                        )

                        Spacer(Modifier.height(12.dp))

                        Text(
                            "Раздел помощника подготовлен для следующего этапа разработки",
                            color = textColor
                        )

                        Spacer(Modifier.height(16.dp))

                        Button(onClick = { screen = "home" }) {
                            Text("На главную")
                        }
                    }
                }

                else -> {
                    Button(onClick = { screen = "home" }) {
                        Text("На главную")
                    }
                }
            }
        }
    }
}
