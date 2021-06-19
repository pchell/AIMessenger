package com.maveri.aimessenger.room.widget

import androidx.recyclerview.widget.RecyclerView

class RoomMessageViewHolder : RecyclerView.ViewHolder {
    constructor(itemView: MessageUserItemView) : super(itemView)
    constructor(itemView: MessageOtherItemView) : super(itemView)
}