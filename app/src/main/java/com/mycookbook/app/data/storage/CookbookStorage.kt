
package com.mycookbook.app.data.storage

import android.content.Context
import com.mycookbook.app.data.model.CookingStep
import com.mycookbook.app.data.model.MediaAttachment
import com.mycookbook.app.data.model.NotebookNote
import com.mycookbook.app.data.model.Recipe
import org.json.JSONArray
import org.json.JSONObject

class CookbookStorage(context: Context) {

    private val preferences = context.getSharedPreferences(
        "my_cookbook_data",
        Context.MODE_PRIVATE
    )

    fun saveRecipes(recipes: List<Recipe>) {
        val array = JSONArray()

        recipes.forEach { recipe ->
            val obj = JSONObject()
            obj.put("id", recipe.id)
            obj.put("title", recipe.title)
            obj.put("description", recipe.description)
            obj.put("category", recipe.category)
            obj.put("ingredients", JSONArray(recipe.ingredients))
            obj.put("steps", stepsToJson(recipe.steps))
            obj.put("photos", mediaToJson(recipe.photos))
            obj.put("videos", mediaToJson(recipe.videos))
            obj.put("isFavorite", recipe.isFavorite)
            obj.put("createdAt", recipe.createdAt)
            obj.put("updatedAt", recipe.updatedAt)
            array.put(obj)
        }

        preferences.edit()
            .putString("recipes", array.toString())
            .apply()
    }

    fun loadRecipes(): List<Recipe> {
        val json = preferences.getString("recipes", "[]")
            ?: return emptyList()

        return try {
            val array = JSONArray(json)
            (0 until array.length()).map { index ->
                val obj = array.getJSONObject(index)

                Recipe(
                    id = obj.optString("id"),
                    title = obj.optString("title"),
                    description = obj.optString("description"),
                    category = obj.optString("category", "Другое"),
                    ingredients = jsonToStrings(
                        obj.optJSONArray("ingredients")
                    ),
                    steps = jsonToSteps(obj.optJSONArray("steps")),
                    photos = jsonToMedia(obj.optJSONArray("photos")),
                    videos = jsonToMedia(obj.optJSONArray("videos")),
                    isFavorite = obj.optBoolean("isFavorite"),
                    createdAt = obj.optLong("createdAt"),
                    updatedAt = obj.optLong("updatedAt")
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun saveNotes(notes: List<NotebookNote>) {
        val array = JSONArray()

        notes.forEach { note ->
            val obj = JSONObject()
            obj.put("id", note.id)
            obj.put("title", note.title)
            obj.put("text", note.text)
            obj.put("photos", mediaToJson(note.photos))
            obj.put("videos", mediaToJson(note.videos))
            obj.put("createdAt", note.createdAt)
            obj.put("updatedAt", note.updatedAt)
            array.put(obj)
        }

        preferences.edit()
            .putString("notes", array.toString())
            .apply()
    }

    fun loadNotes(): List<NotebookNote> {
        val json = preferences.getString("notes", "[]")
            ?: return emptyList()

        return try {
            val array = JSONArray(json)
            (0 until array.length()).map { index ->
                val obj = array.getJSONObject(index)

                NotebookNote(
                    id = obj.optString("id"),
                    title = obj.optString("title"),
                    text = obj.optString("text"),
                    photos = jsonToMedia(obj.optJSONArray("photos")),
                    videos = jsonToMedia(obj.optJSONArray("videos")),
                    createdAt = obj.optLong("createdAt"),
                    updatedAt = obj.optLong("updatedAt")
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun clearAll() {
        preferences.edit().clear().apply()
    }

    private fun stepsToJson(steps: List<CookingStep>): JSONArray {
        val array = JSONArray()

        steps.forEach { step ->
            val obj = JSONObject()
            obj.put("id", step.id)
            obj.put("number", step.number)
            obj.put("title", step.title)
            obj.put("description", step.description)
            obj.put("photos", mediaToJson(step.photos))
            obj.put("videos", mediaToJson(step.videos))
            array.put(obj)
        }

        return array
    }

    private fun jsonToSteps(array: JSONArray?): List<CookingStep> {
        if (array == null) return emptyList()

        return (0 until array.length()).map { index ->
            val obj = array.getJSONObject(index)

            CookingStep(
                id = obj.optString("id"),
                number = obj.optInt("number", index + 1),
                title = obj.optString("title"),
                description = obj.optString("description"),
                photos = jsonToMedia(obj.optJSONArray("photos")),
                videos = jsonToMedia(obj.optJSONArray("videos"))
            )
        }
    }

    private fun mediaToJson(
        media: List<MediaAttachment>
    ): JSONArray {
        val array = JSONArray()

        media.forEach { item ->
            val obj = JSONObject()
            obj.put("id", item.id)
            obj.put("name", item.name)
            obj.put("path", item.path)
            obj.put("type", item.type)
            array.put(obj)
        }

        return array
    }

    private fun jsonToMedia(array: JSONArray?): List<MediaAttachment> {
        if (array == null) return emptyList()

        return (0 until array.length()).map { index ->
            val obj = array.getJSONObject(index)

            MediaAttachment(
                id = obj.optString("id"),
                name = obj.optString("name"),
                path = obj.optString("path"),
                type = obj.optString("type", "PHOTO")
            )
        }
    }

    private fun jsonToStrings(array: JSONArray?): List<String> {
        if (array == null) return emptyList()

        return (0 until array.length()).map { index ->
            array.optString(index)
        }
    }
}
