package com.riso.defalutapp.ui.screen.carList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.riso.defalutapp.R
import com.riso.domain.model.ImageDataModel
import com.riso.imageloader.api.ImageLoader

class ImageListAdapter(
    private val imageLoader: ImageLoader
) : ListAdapter<ImageDataModel, ImageListAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val textId: TextView = view.findViewById(R.id.textId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.textId.text = item.id.toString()
        imageLoader.load(
            url = item.url,
            placeholderRes = R.drawable.ic_launcher_foreground,
            target = holder.imageView
        )
    }

    class DiffCallback : DiffUtil.ItemCallback<ImageDataModel>() {
        override fun areItemsTheSame(old: ImageDataModel, new: ImageDataModel) = old.id == new.id
        override fun areContentsTheSame(old: ImageDataModel, new: ImageDataModel) = old == new
    }
}
