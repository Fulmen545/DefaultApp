package com.riso.defalutapp.ui.screen.carList

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.riso.defalutapp.R
import com.riso.imageloader.api.ImageLoader
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CarListFragment: Fragment(R.layout.fragment_car_list) {

    private val viewModel by viewModel<CarListViewModel>()
    private lateinit var adapter: ImageListAdapter
    private val imageLoader: ImageLoader by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewCarList)
        val placeholderImage = view.findViewById<ImageView>(R.id.placeholderImage)
        val fab = view.findViewById<ExtendedFloatingActionButton>(R.id.fabInvalidate)

        adapter = ImageListAdapter(imageLoader)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        fab.setOnClickListener {
            viewModel.setEvent(CarListContract.Event.InvalidateCaches)
        }
        placeholderImage.setOnClickListener {
            viewModel.setEvent(CarListContract.Event.Reload)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { state ->
                    if (state.images.isEmpty()) {
                        placeholderImage.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    } else {
                        placeholderImage.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        adapter.submitList(state.images)
                    }
                }
            }
        }
    }
}