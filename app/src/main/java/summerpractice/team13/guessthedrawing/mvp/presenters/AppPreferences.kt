package summerpractice.team13.guessthedrawing.mvp.presenters

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {
    private const val NAME = "settings"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    // Saving Time in settings to SharedPreferences
    // getter and setter with Shared Preference
    var time: Int
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getInt("temp", 15)
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putInt("temp", value)
        }

    var mode: String?
        get() = preferences.getString("mode", "Easy")
        set(value) = preferences.edit {
            it.putString("mode", value)
        }
    var language: String?
        get() = preferences.getString("lang", "en")
        set(value) = preferences.edit {
            it.putString("lang", value)
        }
    var sliderMusicValue: Float?
        get() = preferences.getFloat("sliderMusicValue", 50.0F)
        set(value) = preferences.edit {
            it.putFloat("sliderMusicValue", value!!)
        }
    var sliderEffectsValue: Float?
        get() = preferences.getFloat("sliderEffectsValue", 50.0F)
        set(value) = preferences.edit {
            it.putFloat("sliderEffectsValue", value!!)
        }
}