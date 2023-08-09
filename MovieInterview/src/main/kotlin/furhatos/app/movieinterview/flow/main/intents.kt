package furhatos.app.movieinterview.flow.main

import furhatos.nlu.Intent
import furhatos.util.Language

class GenreIntent(var genre : Genres? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "@genre",
            "I like @genre")
    }
}

class Ready : Intent()  {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I'm ready",
            "Let's go",
            "We can start",
            "ready"
        )
    }
}