package com.asseco.ce.parking.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.riso.defalutapp.ui.theme.DefalutAppTheme

abstract class BaseComposeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        activity?.window?.also {
            WindowCompat.setDecorFitsSystemWindows(it, false)
        }
        super.onCreate(savedInstanceState)
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

    @Composable
    protected abstract fun SetContent()
}
