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

class GoodAndYou : Intent()  {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I'm fine thank you, and you?",
            "I'm good, how about you?",
            "I'm doing good thanks, you?",
            "good and you?",
            "well, and you?",
            "I'm good, how are you?",
            "I'm doing well, how are you?"
        )
    }
}
class Good : Intent()  {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I'm fine thank you",
            "I'm good",
            "I'm doing good thanks",
            "good",
            "well"
            )
    }
}