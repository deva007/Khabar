import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

//  Define DataStore (Global)
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

object DataStorageManager {
    private val LOGIN_STATE_KEY = booleanPreferencesKey("is_logged_in")
    private val MSISDN_KEY = stringPreferencesKey("msisdn")
    private val PASSWORD_KEY = stringPreferencesKey("password")

    //  Save login state, mobile number & password
    suspend fun saveUserData(context: Context, isLoggedIn: Boolean, msisdn: String, password: String) {
        context.dataStore.edit { prefs ->
            prefs[LOGIN_STATE_KEY] = isLoggedIn
            prefs[MSISDN_KEY] = msisdn
            prefs[PASSWORD_KEY] = password
        }
    }

    //  Get login state
    fun getLoggedIn(context: Context): Flow<Boolean> {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences()) // Prevent crash
                } else {
                    throw exception
                }
            }
            .map { prefs ->
                prefs[LOGIN_STATE_KEY] ?: false
            }
    }

    //  Get saved mobile number
    fun getMobileNumber(context: Context): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[MSISDN_KEY]
        }
    }

    //  Get saved password
    fun getPassword(context: Context): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[PASSWORD_KEY]
        }
    }

    //  Logout (Clear all stored data)
    suspend fun logout(context: Context) {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
