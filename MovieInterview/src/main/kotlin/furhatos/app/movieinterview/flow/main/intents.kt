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
            "in my house",
            "in my home",
            "house",
            "house",
            "home"
        )
    }
}

class Cinema : Intent()  {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "at the cinema",
            "in the cinema",
            "cinema"
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

class Uncertain : Intent()  {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "i am not sure",
            "not sure",
            "maybe",
            "maybe not"
        )
    }
}

class WhatQuestion : Intent()  {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "what is the question",
            "what was the question",
            "what is the question again",
            "what was the question again",
            "Pardon",
            "Could you repeat that",
            "What",
            "Say what",
            "I did not get that",
            "I did not understand that"
        )
    }
}

class Idontknow : Intent()  {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I don't know",
            "I have no idea",
            "I am not sure",
            "No idea",
            "Don't know"
        )
    }
}