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
fun extractFirstNumber(input: String): Int? {
    /* This function handles two different types of inputs:
        - Sometimes, through FurHats NLU, responses that include numbers realize these differently.
        - If they are in a longer sentence: written-out version, i.e.: seven,eight etc..
        - If they are standalone numbers, they are realized as digits.
        - Solution: Pick up first number in the input string, whether it is written out or a digit,
        - Ignore every other number in the input
    */
    val digitMatch = "\\d+".toRegex().find(input)

    // Try to find the first occurrence of a written-out number
    val wordNumberPatterns = listOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten")
    val wordMatch = wordNumberPatterns.asSequence().map { it to "\\b$it\\b".toRegex().find(input) }.firstOrNull { it.second != null }

    // If neither form is found, return (will lead to reEntry() )
    if (digitMatch == null && wordMatch == null) return null

    // If only one form is found, return it
    if (digitMatch == null) return wordToNumber(wordMatch!!.first)
    if (wordMatch == null) return digitMatch.value.toInt()

    // If both forms are found, return the one that occurs first
    return if (digitMatch.range.first < wordMatch.second!!.range.first) digitMatch.value.toInt() else wordToNumber(wordMatch.first)
}

// Convert written-out numbers to their integer representation
fun wordToNumber(word: String): Int? {
    return when (word) {
        "zero" -> 0
        "one" -> 1
        "two" -> 2
        "three" -> 3
        "four" -> 4
        "five" -> 5
        "six" -> 6
        "seven" -> 7
        "eight" -> 8
        "nine" -> 9
        "ten" -> 10
        else -> null
    }
}
