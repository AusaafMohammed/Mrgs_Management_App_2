package com.example.mrgsmanagementapp.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mrgsmanagementapp.*
//import com.example.mrgsmanagementapp.databinding.ActivityLibrary2Binding

import com.example.mrgsmanagementapp.databinding.ActivityLibraryBinding

class LibraryActivity : AppCompatActivity(), BookClickListener {

    private lateinit var binding: ActivityLibraryBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityLibraryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        populateBooks()

        val libraryActivity = this
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext, 2)
            adapter = CardAdapter(book_list, libraryActivity)
        }
    }

    override fun onClick(book: Book) {
        val intent = Intent(applicationContext, DetailActivity::class.java)
        intent.putExtra(BOOK_ID_EXTRA, book.id)
        startActivity(intent)
    }

    private fun populateBooks() {

        val book1 = Book(
            R.drawable.assassins_blade,
            author = "Sarah J. Maas",
            title = "The Assassin's Blade",
            status = "Not Available - Available on 21/08/2021",
            description = "The novellas are about how Celaena, Adarlan's most feared assassin and Arobynn Hamel's chosen heir, betrays her master by freeing the hundred slaves that Arobynn wanted to trade and sell, with the help of her fellow assassin Sam Cortland.",
        )
        book_list.add(book1)

        val book2 = Book(
            R.drawable.beautiful_struggle,
            author = "Ta-Nehisi Coates",
            title = "The Beautiful Struggle",
            status = "Not Available - Available on 08/08/2021",
            description = "Paul Coates was an enigmatic god to his sons: a Vietnam vet who rolled with the Black Panthers, an old-school disciplinarian and new-age believer in free love, an autodidact who launched a publishing company in his basement dedicated to telling the true history of African civilization. Most of all, he was a wily tactician whose mission was to carry his sons across the shoals of inner-city adolescence—and through the collapsing civilization of Baltimore in the Age of Crack—and into the safe arms of Howard University, where he worked so his children could attend for free.",
                    )
        book_list.add(book2)

        val book3 = Book(
            R.drawable.fatesdivide,
            author = "Veronica Roth",
            title = "The Fates Divide",
            status = "Available",
            description = "The Fates Divide is a richly imagined tale of hope and resilience told in four stunning perspectives. Fate brought them together. Now it will divide them. The lives of Cyra Noavek and Akos Kereseth are ruled by their fates, spoken by the oracles at their births.",
        )
        book_list.add(book3)

        val book4 = Book(
            R.drawable.gloves_off,
            author = "Louisa Reid",
            title = "Gloves Off",
            status = "On hold",
            description = "A page-turning and immersive YA novel in verse, telling the story of Lily who is mercilessly bullied at school and who turns to boxing in an attempt to fight back; a story of hope and resilience breaking through even the most difficult situations. Lily turns sixteen with two very different sides to her life: school, where she is badly bullied, and home with her mum and dad, warm and comforting but with its own difficulties. After a particularly terrible bullying incident, Lily's dad determines to give his daughter the tools to fight back." ,
        )
        book_list.add(book4)

        val book5 = Book(
            R.drawable.imagine_me,
            author = "Tahereh Mafi",
            title = "Imagine Me",
            status = "Available",
            description = "Which is the truth and which is the lie?\n" +
                    "\n" +
                    "Now that Ella knows who Juliette is and what she was created for, things have only become more complicated. As she struggles to understand the past that haunts her and looks to a future more uncertain than ever, the lines between right and wrong—between Ella and Juliette—blur. And with old enemies looming, her destiny may not be her own to control. The day of reckoning for the Reestablishment is coming. But she may not get to choose what side she fights on.",
        )
        book_list.add(book5)

        val book6 = Book(
            R.drawable.one_of_us,
            author = "Karen M. McManus",
            title = "One of us is lying",
            status = "Not Available - Available on 02/09/2021",
            description = "One of Us is Lying follows the gripping story of Bronwyn, Addy, Nate, and Cooper as suspects in the murder of Simon Kelleher. Each of the high school students have secrets that they would do anything to protect, so how far would they go to make sure they're kept out of the spotlight?",
        )
        book_list.add(book6)

        book_list.add(book1)
        book_list.add(book2)
        book_list.add(book3)
        book_list.add(book4)
        book_list.add(book5)
        book_list.add(book6)
    }

}