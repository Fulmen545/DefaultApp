package com.asseco.ce.parking.ui.base

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.riso.defalutapp.R
import com.riso.defalutapp.ui.theme.DefalutAppTheme
import kotlinx.parcelize.Parcelize
import timber.log.Timber
import com.riso.core.extension.TAG
import com.riso.defalutapp.utils.extension.argument

abstract class BaseDialogFragment : DialogFragment(), DialogInterface.OnShowListener {

    companion object {
        const val ARG_CANCELABLE = "ARG_CANCELABLE_ON_TOUCH_OUTSIDE"
        const val ARG_REQUEST_CODE = "ARG_REQUEST_CODE"
        const val ARG_RESULT = "ARG_RESULT"
    }

    protected open val requestCode: String? by argument(ARG_REQUEST_CODE)
    protected open val argCancellable: Boolean by argument(ARG_CANCELABLE, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_DefaultApp_Dialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            setContent {
                DefalutAppTheme {
                    SetContent()
                }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireActivity(), theme).apply {
            setCanceledOnTouchOutside(argCancellable)
            setOnShowListener(this@BaseDialogFragment)
        }
    }

    override fun onDestroyView() {
        // bug in the compatibility library
        val dialog = dialog
        if (dialog != null && retainInstance) {
            dialog.setDismissMessage(null)
        }
        super.onDestroyView()
    }

    override fun onShow(dialogInterface: DialogInterface) {}

    open fun showAllowingStateLoss(manager: FragmentManager, tag: String?) {
        manager.beginTransaction().apply {
            add(this@BaseDialogFragment, tag)
            commitAllowingStateLoss()
        }
    }

    protected open fun <T : BaseDialogResult> setDialogFragmentResult(result: T) {
        requestCode?.let {
            setFragmentResult(it, bundleOf(ARG_RESULT to result))
        } ?: Timber.tag(TAG).w("Dialog request-code is not set. Unable to post result value.")
    }

    protected open fun setDialogFragmentResult(bundle: Bundle) {
        requestCode?.let {
            setFragmentResult(it, bundle)
        } ?: Timber.tag(TAG).w("Dialog request-code is not set. Unable to post result value.")
    }

    @Composable
    protected abstract fun SetContent()

    /**
     * Holder of a string resource holder.
     */
    @Parcelize
    data class StringResourceHolder(val text: String?, val res: Int?) : Parcelable {

        @Composable
        fun getString(): String? {
            return when {
                text == null && res == null -> null
                text != null -> text
                res != null && res != 0 -> stringResource(res)
                res != null && res == 0 -> null
                else -> throw IllegalStateException()
            }
        }
    }

    interface BaseDialogResult

    abstract class BaseDialogBuilder<T : BaseDialogFragment> {

        /**
         * Factory method for [T].
         */
        open fun newInstance(): T {
            return initFragment()
        }

        /**
         * @see [BaseDialogFragment.show]
         */
        open fun show(fragmentManager: FragmentManager, tag: String? = null) {
            initFragment().show(fragmentManager, tag)
        }

        /**
         * @see [BaseDialogFragment.showNow]
         */
        open fun showNow(fragmentManager: FragmentManager, tag: String? = null) {
            initFragment().showNow(fragmentManager, tag)
        }

        /**
         * @see [BaseDialogFragment.showAllowingStateLoss]
         */
        open fun showAllowingStateLoss(fragmentManager: FragmentManager, tag: String? = null) {
            initFragment().showAllowingStateLoss(fragmentManager, tag)
        }

        protected open val requestCode: String? = null
        protected open var cancellable: Boolean = false

        protected abstract fun createFragment(): T

        protected abstract fun prepareArguments(): Bundle

        protected fun Any?.toStringResourceHolder(): StringResourceHolder {
            return when (this) {
                is Int -> StringResourceHolder(text = null, res = this)
                is String -> StringResourceHolder(text = this, res = null)
                null -> StringResourceHolder(text = null, res = null)
                else -> throw IllegalArgumentException()
            }
        }

        protected open fun initFragment(): T = createFragment().apply {
            arguments = prepareArguments().apply {
                putBoolean(ARG_CANCELABLE, this@BaseDialogBuilder.cancellable)
                putString(ARG_REQUEST_CODE, this@BaseDialogBuilder.requestCode)
            }
        }
    }
}

/**
 * Sets the [FragmentResultListener] for a given [requestKey]
 *
 * @see [setFragmentResultListener]
 *
 * @param requestKey request code of the callback
 * @param listener for the requestKey and given result
 */
inline fun <reified T : BaseDialogFragment.BaseDialogResult> Fragment.setDialogFragmentResultListener(
    requestKey: String,
    crossinline listener: ((requestKey: String, result: T) -> Unit)
) {
    setFragmentResultListener(requestKey) { _requestKey, _bundle ->
        if (requestKey == _requestKey) {
            val result = _bundle.getSerializable(BaseDialogFragment.ARG_RESULT) as T?
                ?: _bundle.getParcelable(BaseDialogFragment.ARG_RESULT)
            result?.also {
                listener(_requestKey, it)
            }
        }
    }
}

/**
 * Sets the [FragmentResultListener] for a given [requestKey]SimpleDialogFragment.kt
 *
 * @see [setFragmentResultListener]
 *
 * @param requestKey request code of the callback
 * @param listener for the requestKey, bundle and given result
 */
inline fun <reified T : BaseDialogFragment.BaseDialogResult> Fragment.setDialogFragmentResultListener(
    requestKey: String,
    crossinline listener: ((requestKey: String, bundle: Bundle, result: T) -> Unit)
) {
    setFragmentResultListener(requestKey) { _requestKey, _bundle ->
        if (requestKey == _requestKey) {
            val result = _bundle
                .getSerializable(BaseDialogFragment.ARG_RESULT) as T

            listener(_requestKey, _bundle, result)
        }
    }
}
