package com.example.my_app_agroberries.core.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log

object PackageUtils {
    private const val TAG = "PackageUtils"

    fun getPackageInfo(context: Context): PackageInfo? {
        return try {
            val packageName = context.packageName
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.packageManager.getPackageInfo(
                    packageName,
                    PackageManager.PackageInfoFlags.of(0)
                )
            } else {
                @Suppress("DEPRECATION")
                context.packageManager.getPackageInfo(packageName, 0)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "Package name not found: ${context.packageName}", e)
            null
        }
    }

    fun getVersionName(context: Context): String {
        return getPackageInfo(context)?.versionName ?: "1.0.0"
    }

    fun getVersionCode(context: Context): Long {
        val info = getPackageInfo(context) ?: return 1L
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            info.longVersionCode
        } else {
            @Suppress("DEPRECATION")
            info.versionCode.toLong()
        }
    }
}