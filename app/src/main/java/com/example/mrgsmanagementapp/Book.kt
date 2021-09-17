package com.example.mrgsmanagementapp

var book_list = mutableListOf<Book>()

val BOOK_ID_EXTRA = "bookExtra"

class Book(
    var cover: Int,
    var author: String,
    var title: String,
    var status: String,
    var description: String,
    var id: Int? = book_list.size
)