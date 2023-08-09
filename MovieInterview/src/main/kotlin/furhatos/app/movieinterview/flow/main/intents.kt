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
            "ready",
            "Yes",
            "already",
            "start"
        )
    }
}

class Home : Intent()  {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "at home",
            "house",
            "home"
        )
    }
}

class Cinema : Intent()  {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "at the cinema",
            "movies",
            "at the movies"
        )
    }
}

class Both : Intent()  {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "i like both",
            "both"
        )
    }
}

class IdontKnow : Intent()  {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "i don't know",
            "I have no idea",
            "no",
            "no clue"
        )
    }
}