package com.nabinbhandari.android.permissions

import android.content.Context
import java.util.ArrayList

/**
 * Minimal compatibility shim for the old Android-Permissions API
 * used by this project. It only declares the callbacks with the
 * signatures your code expects. Actual permission requesting is
 * handled elsewhere (Dexter or your own flow).
 */
class PermissionOptions

open class PermissionHandler {
    open fun onGranted() {}

    open fun onDenied(context: Context?, deniedPermissions: ArrayList<String>?) {}

    /**
     * Older versions returned a boolean from onBlocked to signal whether
     * the caller should open Settings automatically. Your code uses the
     * return value, so keep it as Boolean.
     */
    open fun onBlocked(
        context: Context?,
        deniedPermissions: ArrayList<String>?
    ): Boolean {
        return false
    }

    /**
     * Your code calls onJustBlocked with three arguments.
     * Keep a third parameter for “permanently denied” list.
     */
    open fun onJustBlocked(
        context: Context?,
        deniedPermissions: ArrayList<String>?,
        permanentlyDenied: ArrayList<String>?
    ) { }
}

/**
 * Convenience facade with the static-like API your code imports.
 * We only provide the method signature; call sites can continue compiling.
 */
object Permissions {
    @JvmStatic
    fun check(
        context: Context,
        permissions: Array<String>,
        rationale: String? = null,
        options: PermissionOptions? = null,
        handler: PermissionHandler
    ) {
        // No-op shim: immediately report "denied" so call sites execute their paths.
        // If you’re also using Dexter to do the real work, this stub won’t be called.
        handler.onDenied(context, arrayListOf())
    }
}