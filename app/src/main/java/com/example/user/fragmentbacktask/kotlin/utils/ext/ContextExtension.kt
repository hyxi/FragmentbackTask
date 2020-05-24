package com.example.user.fragmentbacktask.kotlin.utils.ext

import android.animation.Animator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 * screen width in pixels
 */
inline val Context.screenWidth
    get() = resources.displayMetrics.widthPixels

/**
 * screen height in pixels
 */
inline val Context.screenHeight
    get() = resources.displayMetrics.heightPixels

inline val Context.isNetworkAvailable: Boolean
    @SuppressLint("MissingPermission")
    get() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo?.isConnectedOrConnecting ?: false
    }

inline fun <reified T : Activity> Activity.startActivity() {
    startActivity(Intent(this, T::class.java))
}

inline fun <reified F : Fragment> Context.newInstance(vararg args: Pair<String, Any>): Fragment {
    val bundle = Bundle()
    args.let {
        for (arg in args) {
            bundle[arg.first] = arg.second
        }
    }
    return Fragment.instantiate(this, F::class.java.name, bundle)
}

operator fun Bundle.set(key: String, value: Any) {
    when (value) {
        is String -> putString(key, value)
        is Int -> putInt(key, value)
        is Double -> putDouble(key, value)
        is Float -> putFloat(key, value)
        is Boolean -> putBoolean(key, value)
        is Long -> putLong(key, value)
        is Short -> putShort(key, value)
        else -> Throwable("can receive base data type")
    }
}

@Suppress("UNCHECKED_CAST")
operator fun <T> Bundle.get(key: String): T? {
    return get(key) as T
}

@Suppress("UNCHECKED_CAST")
fun <T> getExtra(extraName: String, oldExtra: T?, bundle: Bundle): T {
    return oldExtra ?: bundle.get(extraName) as T
}

fun Context.string(@StringRes id: Int): String = getString(id)

fun Context.color(@ColorRes id: Int): Int? = ContextCompat.getColor(this, id)

fun Context.drawable(@DrawableRes id: Int): Drawable? = ContextCompat.getDrawable(this, id)

fun Context.inflateLayout(@LayoutRes layoutId: Int, parent: ViewGroup? = null, attachToRoot: Boolean = false): View = LayoutInflater.from(this).inflate(layoutId, parent, attachToRoot)

/**
 * 获取当前app的版本号
 */
fun Context.getAppVersion(): String {

    val appContext = applicationContext
    val manager = appContext.getPackageManager()
    try {
        val info = manager.getPackageInfo(appContext.getPackageName(), 0)

        if (info != null)
            return info.versionName

    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }

    return ""
}

fun Context.getAppVersionCode(): Int {

    val appContext = applicationContext
    val manager = appContext.getPackageManager()
    try {
        val info = manager.getPackageInfo(appContext.getPackageName(), 0)

        if (info != null)
            return info.versionCode

    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }

    return 0
}
