package furhatos.app.movieinterview.flow

import furhatos.app.movieinterview.flow.main.*
import furhatos.app.movieinterview.setting.*
import furhatos.flow.kotlin.*
import furhatos.nlu.common.DontKnow
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val LastMovieWatched: State = state(Parent) {
    onEntry {
        furhat.say{
        +"Alright!"
        +delay(500)
        +"Let’s start with a light question."
        }
        furhat.ask("What was the last movie you watched in a cinema?", timeout = 7000)
    }
    onResponse {
        goto(RecommendMovie)
    }
    onNoResponse {
        furhat.ask("Do you remember what the last movie you watched in cinema was?")
    }
}

val RecommendMovie: State = state(Parent) {
    onEntry {
        furhat.ask("Would you recommend this movie to others?", timeout = 2000)
    }
    onResponse<Yes> {
        furhat.say("Great, I heard some very positive things about it as well.")
        goto(RememberMovieState)
    }
    //FIXME: Doesn't work
    onResponse<No>{
        furhat.say("Oh. Do you have a specific reason for that?")
        furhat.listen(timeout = 10000)
        goto(RememberMovieState)
    }
    onNoResponse {
        furhat.ask("Would you recommend this movie to others?")
    }
}

val RememberMovieState: State = state(Parent) {
    onEntry {
        furhat.say("I see. Moving on to the next question.")
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
        furhat.ask("If you had to go for just one: What would be your favourite movie genre?")
    }
    onReentry {
        furhat.ask("What is your favourite genre?")
    }

    // Since we only allow GenreIntent as an answer, this secures that getMoviesByGenre will have a return value
    onResponse<GenreIntent>{
        favouriteGenre = it.intent.genre.toString()
        filmfromGenre = getWorstMovieByGenre(favouriteGenre)
        furhat.say("Cool, I like $favouriteGenre movies as well.")
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
}

val SeenMovieState: State = state(Parent) {
    onEntry {
        furhat.ask("Have you seen $filmfromGenre")
    }

    onResponse<Yes>{
        furhat.say{
            +"Well, that is interesting."
            +"According to IMDB, it is the lowest rated $favouriteGenre movie at the moment."
            +"Did you like it?" //#FIXME
        }
        goto(HighestRatedOfGenre)
    }

    onResponse<No>{
        furhat.say{
            +"I am not surprised. It is the lowest rated $favouriteGenre movie on IMDB at the moment."
            +delay(200)
        }
        goto(HighestRatedOfGenre)
    }
}

val HighestRatedOfGenre: State = state(Parent) {
    onEntry {
        furhat.ask("What do you think is the highest rated movie in this genre?")
    }
    onResponse {
        goto(HaveYouSeenIt)
    }
}

val HaveYouSeenIt: State = state(Parent) {
    onEntry {
        furhat.ask("Have you seen it?")
    }
    onResponse{
        goto(MainActors)
    }
}

val MainActors: State = state(Parent){
    onEntry {
        furhat.ask("Do you remember who the main actors were?")
    }
    onResponse{
        goto(WatchItAgain)
    }
    onNoResponse {
        furhat.ask("Who were the main actors of that movie?")
    }
}

val WatchItAgain: State = state(Parent){
    onEntry {
        furhat.ask("Would you watch it again?")
    }
    onResponse{
        goto(RecommendMeSomething)
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

