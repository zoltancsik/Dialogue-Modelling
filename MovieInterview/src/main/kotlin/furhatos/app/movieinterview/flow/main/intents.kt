package furhatos.app.movieinterview.flow.main

import furhatos.nlu.Intent
import furhatos.util.Language
import kotlin.random.Random

class GenreIntent(var genre : Genres? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "@genre",
            "I like @genre")
    }
}
