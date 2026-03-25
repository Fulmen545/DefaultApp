package com.riso.defalutapp.utils.extension

import androidx.fragment.app.Fragment
import com.riso.core.extension.TAG
import kotlin.properties.ReadOnlyProperty

/**
 * Shortcut for parsing arguments.
 *
 * @param T type of the argument, can be nullable to make the argument optional
 * @throws RuntimeException when [T] is not optional (nullable) and no argument has been found
 */
inline fun <reified T : Any?> Fragment.argument(argName: String): ReadOnlyProperty<Fragment, T> =
    ReadOnlyProperty { thisRef, _ ->
        try {
            arguments?.get(argName) as T
        } catch (npe: NullPointerException) {
            throw RuntimeException("${thisRef.TAG} does not supply argument named '$argName'", npe)
        }
    }

/**
 * Shortcut for parsing arguments.
 *
 * @param T type of the argument, can be nullable to make the argument optional
 * @throws RuntimeException when [T] is not optional (nullable) and no argument has been found
 */
inline fun <reified T : Any?> Fragment.argument(argName: String, def: T): ReadOnlyProperty<Fragment, T> =
    ReadOnlyProperty { thisRef, _ ->
        try {
            arguments?.get(argName) as T
        } catch (npe: NullPointerException) {
            def
        } catch (e: Exception) {
            throw RuntimeException("${thisRef.TAG} does not supply argument named '$argName'", e)
        }
    }
