package furhatos.app.movieinterview.flow

import furhatos.app.movieinterview.flow.main.*
import furhatos.app.movieinterview.setting.*
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val AskIfRecommends: State = state(Parent) {
    onEntry {
        furhat.ask("Is it a movie you would recommend?")
    }
    onResponse {
        goto(RememberMovieState)
    }
    onNoResponse {
        furhat.ask("Would you recommend this movie?")
    }
}

val RememberMovieState: State = state(Parent) {
    onEntry {
        furhat.ask("Do you remember your first movie experience ever  by any chance?", timeout = 2000)
    }
    onResponse{
        goto(WhatDoYouRemember)
    }
    onNoResponse {
        furhat.ask("Do you remember your first movie experience ever?")
    }
}

val WhatDoYouRemember: State = state(Parent) {
    onEntry {
        furhat.ask("What do you remember about it?")
    }
    onResponse {
        goto(CinemaNumbers)
    }
    onNoResponse {
        furhat.ask("What do you remember about your first movie experience?")
    }
}

val CinemaNumbers: State = state(Parent) {
    onEntry {
        furhat.ask{
                    +"Last year the people saw on average 1 movie a year in the cinema."
                    +"How about you? How often do you go to the cinema per year?"
        }
    }
    onResponse{
        goto(IsCinemaDying)
    }
    onNoResponse {
        furhat.ask("How often do you go to the cinema?")
    }
}

val IsCinemaDying: State = state(Parent){
    onEntry {
        furhat.say{
            + "The number of visits have been continuously going down."
            + "In 2002 there were still 2 visits on average."
        }
        furhat.ask("Is cinema dying?", timeout = 2000)
    }
    onResponse{
        goto(WhyCinemaDying)
    }
    onNoResponse {
        furhat.ask("Do you think that cinema is dying?")
    }
}

val WhyCinemaDying: State = state(Parent){
    onEntry {
        furhat.ask("Why do you think that?")
    }
    onResponse{
        furhat.say("Alright")
        goto(AskGenreState)
    }
}

val AskGenreState: State = state(Parent) {
    onEntry {
        furhat.say {
            +"Now switching to your personal movie taste."
            +delay(2000)
        }
        furhat.say("If you had to go for just one: What would be your favourite movie genre?")
    }
    onReentry {
        furhat.ask("What is your favourite genre?")
    }

    // Since we only allow GenreIntent as an answer, this secures that getMoviesByGenre will have a return value
    onResponse<GenreIntent>{
        favouriteGenre = it.intent.genre.toString()
        // Here, define the worst rated movie for each genre
        filmfromGenre = getMovieByGenre(favouriteGenre)

        furhat.say("Oh, like $filmfromGenre?")
        goto(SeenMovieState)
    }

    onNoResponse {
        furhat.say{
            +"I am familiar with the following genres:"
            + delay(200)
            + Genres().optionsToText()
        }
        furhat.ask("What would be your favourite genre?")
    }

    onResponseFailed{
        reentry()
    }
}

val SeenMovieState: State = state(Parent) {
    onEntry {
        furhat.ask("Have you seen $filmfromGenre?")
    }

    onResponse<Yes>{
        furhat.say{
            +"I think..."
            +delay(200)
            +"i watched it in the cinema with friends when it came out."
            +delay(200)
        }
        goto(WhereYouWatched)
    }

    onResponse<No>{
        val movieName = filmfromGenre.toString()
        val plot = getplotbyMovie(movieName)
        furhat.say{
            +"It's a movie about $plot" // gets a specific description from intents
            +"I enjoyed watching it."
            +"It certainly is a recommendation from me."
            +delay(200)
        }
        filmfromGenre = getMovieByGenre(favouriteGenre)
        furhat.ask("How about $filmfromGenre? Have you seen it?")
    }
    onNoResponse {
        furhat.ask("Have you seen $filmfromGenre?")
    }
}

val WhereYouWatched: State = state(Parent) {
    onEntry {
        furhat.ask("Do you remember where you watched the movie?")
    }
    onResponse {
        goto(MainActorState)
    }
}

val MainActorState: State = state(Parent) {
    onEntry {
        furhat.ask("Do you remember who the main actors were?")
    }

    onResponse<Yes> {
        furhat.say("Yeah, they were amazing")
        goto(WatchitAgainState)
    }

    onResponse<No> {
        val movie = filmfromGenre.toString()
        val actor = getactorbyMovie(movie)
        furhat.say("$actor played the main character and gave an amazing performance")
        goto(WatchitAgainState)
    }
    onNoResponse {
        furhat.ask("Do you remember who the main actors in $filmfromGenre were?")
    }
}

val WatchitAgainState: State = state(Parent) {
    onEntry {
        furhat.ask("Would you watch it again?")
    }

    onResponse<Yes> {
        furhat.say{
            +"I"
            +delay(300)
            +"think I would too."
            +"I have only seen it once so far"
        }
        goto(RecommendMeSomething)
    }

    onResponse<No> {
        furhat.say {
            +"Me neither"
            +delay(200)
            +"Because I have seen it three times by now"
        }
        goto(RecommendMeSomething)
    }
    onNoResponse {
        furhat.ask("Would you watch $filmfromGenre again?")
    }
}

val RecommendMeSomething: State = state(Parent) {
    onEntry {
        furhat.ask("Do you have a movie recommendation for me?")
    }

    onResponse<Yes> {
        furhat.ask("Which one?")
    }

    onResponse{
        goto(RecommendationState)
    }
    onNoResponse {
        furhat.ask("Do you have a movie recommendtaion for me?")
    }
}

val RecommendationState: State = state(Parent){
    onEntry{
        furhat.ask{
            +"I'm not sure I've seen it"
            +delay(200)
            +"What is the plot of the movie?"
        }
    }
    onResponse {
        goto(GiveMeRecommendation)
    }
    onNoResponse {
        furhat.ask("What is the plot of the movie?")
    }
}

val GiveMeRecommendation: State = state(Parent){
    onEntry {
        furhat.ask("And what did you like about it?")
    }
    onResponse{
        furhat.say{
            +"Interesting."
            +delay(200)
            +"Thanks for this recommendation."
            +delay(200)
            +"Now to the final question."
            +"What are your absolute favourite movies?"
        }
        goto(LastQuestion)
    }
}

val LastQuestion: State = state(Parent){
    onEntry {
        furhat.ask("If you had to choose a couple you have to watch for the rest of your life?")
    }
    onResponse{
        goto(EndInterAction)
    }
    onNoResponse {
        furhat.ask("What did you particularly like about that movie")
    }
}

val EndInterAction: State = state(Parent) {
    onEntry {
        furhat.say("Thank you so much for taking the time")
        goto(Idle)
    }
}

