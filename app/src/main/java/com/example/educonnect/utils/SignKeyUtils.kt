package com.example.educonnect.utils

import android.content.Context
import android.content.pm.PackageManager
import android.util.Base64
import java.security.MessageDigest

object SignKeyUtils {
    fun getApplicationHashKey(context: Context): String? {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
            for (signature in packageInfo.signatures!!) {
                val messageDigest = MessageDigest.getInstance("SHA")
                messageDigest.update(signature.toByteArray())
                val hashKey = Base64.encodeToString(messageDigest.digest(), Base64.DEFAULT).trim()
                if (hashKey.isNotEmpty()) {
                    return hashKey
                }
            }
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}