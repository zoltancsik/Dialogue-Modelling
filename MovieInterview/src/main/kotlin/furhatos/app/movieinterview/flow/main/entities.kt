package furhatos.app.movieinterview.flow.main

import furhatos.nlu.EnumEntity
import furhatos.util.Language
import java.time.LocalTime

class Genres : EnumEntity(speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("action", "adventure", "animation", "comedy",
            "crime", "documentary", "drama", "fantasy", "horror",
            "romance", "sci-fi", "thriller", "western")
    }
}

fun getWorstMovieByGenre(genre: String): String {
    val moviesByGenre = mapOf(
        "action" to "Chaos Rising",
        "adventure" to "Polaris, the Space Submarine and the Mystery of the Polar Night",
        "animation" to "Sunflower",
        "comedy" to "Monty Python and the Holy Grail",
        "crime/mystery" to "James Bond",
        "documentary" to "Finding Home: Journey to MLB",
        "documentaries" to "Finding Home: Journey to MLB",
        "drama" to "12 years a slave",
        "fantasy" to "Lord of the Rings",
        "horror" to "Saw",
        "romance" to "La la land",
        "sci-fi" to "Dune",
        "science fiction" to "Dune",
        "thriller" to "The silence of the lambs",
        "western" to "The good, the bad and the ugly"
    )

    return moviesByGenre[genre] ?: "I don't really know any movies in that genre."
}

fun getBestMovieByGenre(genre: String): String {
    val moviesByGenre = mapOf(
        "action" to "The Lord of the Rings: The Return of the King",
        "adventure" to "Everything Everywhere All at Once",
        "animation" to "WALL-E",
        "comedy" to "Bird man",
        "crime/mystery" to "No Country for Old Men",
        "documentary" to "Citizen four",
        "documentaries" to "Citizen four",
        "drama" to "Moonlight",
        "fantasy" to "The Shape of Water",
        "horror" to "Parasite",
        "romance" to "The Artist",
        "sci-fi" to "Dune",
        "science fiction" to "Dune",
        "thriller" to "The Departed",
        "western" to "Dances with Wolves"
    )

    return moviesByGenre[genre] ?: "I don't really know any movies in that genre."
}

fun getTimeBasedGreeting(): String {
    val currentTime = LocalTime.now()
    return when {
        // Says good morning till 12 am
        currentTime.isBefore(LocalTime.NOON) -> "Good morning"
        // Says good afternoon till 18 o'clock
        currentTime.isBefore(LocalTime.of(18, 0)) -> "Good afternoon"
        else -> "Good evening"
    }
}

fun getDayTimeBasedOnTime(): String {
    val currentTime = LocalTime.now()
    return when {
        currentTime.isBefore(LocalTime.NOON) -> "this morning"
        currentTime.isBefore(LocalTime.of(18, 0)) -> "this afternoon"
        else -> "tonight"
    }
}