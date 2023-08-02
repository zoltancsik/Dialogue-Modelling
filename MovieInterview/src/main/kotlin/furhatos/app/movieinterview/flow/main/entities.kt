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

fun getMovieByGenre(genre: String): String {
    val moviesByGenre = mapOf(
        "action" to "Die hard, John Wick, The Matrix",
        "adventure" to "Indiana Jones, Avatar, Pirates of the Caribbean",
        "animation" to "Toy Story, Shrek, Kung-Fu Panda",
        "comedy" to "Monty Python and the Holy Grail, Hangover, Borat",
        "crime/mystery" to "James Bond, The Sixth Sense, The Green Mile",
        "drama" to "12 years a slave, Billy Elliot, Gladiator",
        "fantasy" to "Lord of the Rings, Thor, Willy Wonka and the chocolate fabric",
        "horror" to "Saw, The Shining, The Ring",
        "romance" to "La la land, Titanic, When Harry met Sally",
        "sci-fi" to "Dune, Star Wars, The Terminator",
        "thriller" to "The silence of the lambs, The prestige, Everything everywhere all at once",
        "western" to "The good, the bad and the ugly, No country for old man, Django unchained"
    )
    val moviesString = moviesByGenre[genre] ?: return "I don't really know any movies in that genre."
    val moviesList = moviesString.split(", ")
    return moviesList[Random.nextInt(moviesList.size)]
}

fun getplotbyMovie(movie: String): String {
    val moviesByGenre = mapOf(
        "John Wick" to "a guy avenging the death of his beloved dog.",
        "The Matrix" to "Some sci-fi stuff that I never really understood.",
        "Die hard" to "a random guy just always killing everyone.",
        "Indiana Jones" to "a professor hunting treasures",
        "Avatar" to "a war on an alien planet",
        "Pirates of the Caribbean" to "a pirate stealing stuff",
        "Toy Story" to "toys coming alive",
        "Shrek" to "an ogre saving the world",
        "Kung-Fu Panda" to "A panda that does kung-fu stuff"
    )
    return moviesByGenre[movie] ?: "I don't really know the plot of that movie either. It was a long time ago"
}

fun getactorbyMovie(movie: String): String {
    val moviesByGenre = mapOf(
        "John Wick" to "Keanu Reeves",
        "The Matrix" to "Keanu Reeves",
        "Die hard" to "Bruce Willis."
    )
    return moviesByGenre[movie] ?: "I don't really remember either"
}


