
package com.mycookbook.app.data.repository

import android.content.Context
import com.mycookbook.app.data.model.NotebookNote
import com.mycookbook.app.data.model.Recipe
import com.mycookbook.app.data.storage.CookbookStorage

class CookbookRepository(context: Context) {

    private val storage = CookbookStorage(context.applicationContext)

    fun getRecipes(): List<Recipe> = storage.loadRecipes()

    fun getNotes(): List<NotebookNote> = storage.loadNotes()

    fun addRecipe(recipe: Recipe) {
        val recipes = getRecipes().toMutableList()
        recipes.add(recipe)
        storage.saveRecipes(recipes)
    }

    fun updateRecipe(recipe: Recipe) {
        val recipes = getRecipes().map {
            if (it.id == recipe.id) {
                recipe.copy(updatedAt = System.currentTimeMillis())
            } else {
                it
            }
        }
        storage.saveRecipes(recipes)
    }

    fun deleteRecipe(id: String) {
        storage.saveRecipes(
            getRecipes().filterNot { it.id == id }
        )
    }

    fun toggleFavorite(id: String) {
        val recipes = getRecipes().map {
            if (it.id == id) {
                it.copy(
                    isFavorite = !it.isFavorite,
                    updatedAt = System.currentTimeMillis()
                )
            } else {
                it
            }
        }

        storage.saveRecipes(recipes)
    }

    fun getFavoriteRecipes(): List<Recipe> {
        return getRecipes().filter { it.isFavorite }
    }

    fun addNote(note: NotebookNote) {
        val notes = getNotes().toMutableList()
        notes.add(note)
        storage.saveNotes(notes)
    }

    fun updateNote(note: NotebookNote) {
        val notes = getNotes().map {
            if (it.id == note.id) {
                note.copy(updatedAt = System.currentTimeMillis())
            } else {
                it
            }
        }

        storage.saveNotes(notes)
    }

    fun deleteNote(id: String) {
        storage.saveNotes(
            getNotes().filterNot { it.id == id }
        )
    }

    fun clearAllData() {
        storage.clearAll()
    }
}
