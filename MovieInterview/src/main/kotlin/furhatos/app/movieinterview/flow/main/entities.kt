package furhatos.app.movieinterview.flow.main

import furhatos.nlu.EnumEntity
import furhatos.nlu.ListEntity
import furhatos.util.Language

class ListOfGenres : ListEntity<Genres>()

class Genres : EnumEntity(speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("action", "adventure", "animation", "comedy", "crime",
                      "drama", "fantasy", "horror", "romance", "sci-fi",
                      "thriller", "western")
    }
}
