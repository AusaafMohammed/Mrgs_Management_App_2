package com.example.mrgsmanagementapp

import androidx.recyclerview.widget.RecyclerView
import com.example.mrgsmanagementapp.databinding.CardCellBinding

class CardViewHolder (
    private val cardCellBinding: CardCellBinding,
    private val clickListener: BookClickListener
) : RecyclerView.ViewHolder(cardCellBinding.root)
{
    fun bindBook(book: Book)
    {
        cardCellBinding.bookCover.setImageResource(book.cover)
        cardCellBinding.title.text = book.title
        cardCellBinding.author.text = book.author
        cardCellBinding.status.text = book.status

        cardCellBinding.cardView.setOnClickListener{
            clickListener.onClick(book)
        }
    }
}