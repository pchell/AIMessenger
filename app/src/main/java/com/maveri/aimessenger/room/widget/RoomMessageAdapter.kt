package com.maveri.aimessenger.room.widget

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.maveri.aimessenger.model.Message

class RoomMessageAdapter : ListAdapter<Message, RoomMessageViewHolder>(DIFF_CALLBACK) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Message.User -> MESSAGE_USER
            is Message.Other -> MESSAGE_OTHER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomMessageViewHolder {
        return when (viewType) {
            MESSAGE_USER -> RoomMessageViewHolder(MessageUserItemView(parent.context))
            MESSAGE_OTHER -> RoomMessageViewHolder(MessageOtherItemView(parent.context))
            else -> error("Unknown item type")
        }
    }

    override fun onBindViewHolder(holder: RoomMessageViewHolder, position: Int) {
        (holder.itemView as? MessageUserItemView)?.setItem(getItem(position))
        (holder.itemView as? MessageOtherItemView)?.setItem(getItem(position))
    }

    companion object {
        const val MESSAGE_USER = 0
        const val MESSAGE_OTHER = 1

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Message, newItem: Message) =
                oldItem == newItem
        }
    }
}