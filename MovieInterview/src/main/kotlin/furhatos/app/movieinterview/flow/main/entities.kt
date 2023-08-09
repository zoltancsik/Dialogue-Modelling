package furhatos.app.movieinterview.flow.main

import furhatos.nlu.EnumEntity
import furhatos.util.Language
import kotlin.random.Random

class Genres : EnumEntity(speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("action", "adventure", "animation", "comedy", "crime",
            "drama", "fantasy", "horror", "romance", "sci-fi",
            "thriller", "western")
    }
}

fun getWorstMovieByGenre(genre: String): String {
    val moviesByGenre = mapOf(
        "action" to "Chaos Rising",
        "adventure" to "Indiana Jones",
        "animation" to "Toy Story",
        "comedy" to "Monty Python and the Holy Grail",
        "crime/mystery" to "James Bond",
        "drama" to "12 years a slave",
        "fantasy" to "Lord of the Rings",
        "horror" to "Saw",
        "romance" to "La la land",
        "sci-fi" to "Dune",
        "thriller" to "The silence of the lambs",
        "western" to "The good, the bad and the ugly"
    )

    return moviesByGenre[genre] ?: "I don't really know any movies in that genre."
}

fun getactorbyMovie(movie: String): String {
    val moviesByGenre = mapOf(
        "John Wick" to "Keanu Reeves",
        "The Matrix" to "Keanu Reeves",
        "Die hard" to "Bruce Willis."
    )
    return moviesByGenre[movie] ?: "I don't really remember either"
}