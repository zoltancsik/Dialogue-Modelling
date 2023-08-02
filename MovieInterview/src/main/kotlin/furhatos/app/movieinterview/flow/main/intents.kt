package furhatos.app.movieinterview.flow.main

import furhatos.nlu.Intent
import furhatos.util.Language

class GenreIntent(var genre : ListOfGenres? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "@genre",
            "I like @genre")
    }
}