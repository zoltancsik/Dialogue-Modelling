package furhatos.app.movieinterview.flow

import furhatos.app.movieinterview.flow.main.*
import furhatos.app.movieinterview.setting.*
import furhatos.flow.kotlin.*
import furhatos.nlu.common.DontKnow
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import kotlinx.coroutines.withTimeout
import org.apache.commons.io.filefilter.TrueFileFilter

val LastMovieWatched: State = state(Parent) {
    onEntry {
        furhat.say{
            +"Alright!"
            +delay(500)
            +"Let’s start with a light question."
        }
        furhat.ask("What was the last movie you watched in the cinema?", timeout = 7000)
    }
    onResponse {
        goto(RecommendMovie)
    }
    onNoResponse {
        furhat.ask("Do you remember what the last movie you watched in cinema was?", timeout = 7000)
    }
}

val RecommendMovie: State = state(Parent) {
    onEntry {
        furhat.ask("Would you recommend this movie to others?", timeout = 10000)
    }
    onResponse<Yes> {
        goto(RememberMovieState)
    }
    onResponse<No>{
        goto(WhyNotLikeMovie)
    }
    onNoResponse {
        furhat.ask("Would you recommend it to others?", timeout = 10000)
    }
}

val WhyNotLikeMovie: State = state(Parent){
    onEntry {
        furhat.ask("Why not? Was there anything specific you did not like about it?", timeout = 10000)
    }
    onResponse {
        goto(RememberMovieState)
    }
    onNoResponse {
        furhat.ask("What did you not like about it", timeout = 10000)
    }
}

val RememberMovieState: State = state(Parent) {
    onEntry {
        furhat.say {
            +"I see. Moving on to the next question."
            +delay(500) // pause
        }
        furhat.ask("Do you remember the first time you went to the cinema?", timeout = 10000)
    }
    onResponse<No>{
        furhat.say{
            +"Understandable. I also don't remember."
            +delay(1000)
        }
        furhat.say()
        goto(CinemaNumbers)
    }
    onResponse<Yes>{
        goto(WhatDoYouRemember)
    }
    onNoResponse {
        furhat.ask("Do you remember your first movie experience ever?", timeout = 10000)
    }
}

val WhatDoYouRemember: State = state(Parent) {
    onEntry {
        furhat.ask("What do you still remember about it? Like movie, cinema or year", timeout = 10000)
    }
    onResponse {
        goto(CinemaNumbers)
    }
    onNoResponse {
        furhat.ask("What do you remember about your first movie experience?", timeout = 10000)
    }
}

val CinemaNumbers: State = state(Parent) {
    onEntry {
        furhat.ask{
            +"Let us move on to the next topic."
            +delay(500)
            +"According to the research we did for this Interview,"
            +"Last year the average German only went to see 1 movie in the cinema."
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
            + "Interesting. "
            +delay(500)
            + "The average number of visits to the cinema has been declining."
            + "If we look back in time, in 2002 there were still 2 visits per year on average."
            +delay(500)
        }
        furhat.ask("Do you think that this decline has to do with the rise of streaming services?", timeout = 10000)
    }
    onResponse{
        goto(WhyCinemaDying)
    }
    onNoResponse {
        furhat.ask("Do you think that there is a correlation between the declining cinema numbers and streaming services?")
    }
}

val HomeOrCinema: State = state(Parent){
    onEntry {
        furhat.ask("What about you? What is your preference? Do you rather watch movies at home or at the cinema?", timeout = 10000)
    }
    onResponse<Cinema>{
        furhat.say("Right, I guess it's just a different feeling when you get to see movies on the big screen.")
        goto(PrepareGenreState)
    }
    onResponse<Home> {
        furhat.say("Right, I guess it is more comfortable than going to the cinema.")
        goto(PrepareGenreState)
    }
    onResponse<Both> {
        furhat.say("I agree, I like watching the newest movies in the cinema, but I enjoy movie nights at home much more.")
        goto(PrepareGenreState)
    }
}

val WhyCinemaDying: State = state(Parent){
    onEntry {
        furhat.ask("Could you elaborate on that?")
    }
    onResponse{
        goto(HomeOrCinema)
    }
}

val PrepareGenreState: State = state(Parent){
    onEntry {
        furhat.say {
            +"Alright."
            +delay(100)
            +"Lets stay on this topic."
            +"Your preferences when it comes to watching movies."
            +delay(100)
        }
        furhat.ask("If you want to watch something, is it important to you that it received good ratings or even was awarded an Oscar?")
    }
    onResponse<Yes> {
        ratingImportant = true
        goto(AskGenreState)
    }
    onResponse<No> {
        ratingImportant = false
        goto(AskGenreState)
    }
}

val AskGenreState: State = state(Parent) {
    onEntry {
        furhat.say("I see.")
        furhat.ask("If you had to go for just one: What would be your favourite movie genre?", timeout = 10000)
    }
    onReentry {
        furhat.ask("What is your favourite genre?", timeout = 10000)
    }

    // Since we only allow GenreIntent as an answer, this secures that getMoviesByGenre will have a return value
    onResponse<GenreIntent>{
        favouriteGenre = it.intent.genre.toString()
        filmfromGenre = getWorstMovieByGenre(favouriteGenre)
        furhat.say("I like $favouriteGenre movies as well.")
        goto(SeenMovieState)
    }

    onNoResponse {
        furhat.say{
            +"I am familiar with the following genres:"
            + delay(200)
            + Genres().optionsToText()
        }
        furhat.ask("Which one of those would be your favourite genre?")
    }
}

val SeenMovieState: State = state(Parent) {
    onEntry {
        furhat.ask("Have you seen $filmfromGenre")
    }
    onResponse<Yes>{
        furhat.say{
            +"I am surprised."
            +"According to IMDB, it is the lowest rated $favouriteGenre movie at the moment."
        }
        goto(HighestRatedOfGenre)
    }
    onResponse<No>{
        if(ratingImportant == true) {
            furhat.say {
                +"That is not surprising."
                +"It is the lowest rated $favouriteGenre movie on IMDB at the moment."
                +"Since ratings matter to you, it makes sense that you haven't seen it."
                +delay(200)
            }
        }
        else
            furhat.say {
                +"Well, that makes sense"
                +"It is the lowest rated $favouriteGenre movie on IMDB at the moment."
                +delay(200)
            }
        goto(HighestRatedOfGenre)
    }
}

val HighestRatedOfGenre: State = state(Parent) {
    onEntry {
        filmfromGenre = getBestMovieByGenre(favouriteGenre)
        furhat.ask("How about $filmfromGenre, have you seen it?")
    }
    onResponse<Yes> {
        if(ratingImportant == true) {
            furhat.say("I thought so, it is an Oscar nominated movie.")
            userseenMostPopularMovie = true
        }
        else{
            furhat.say{
                +"It is an Oscar nominated movie after all."
            }
        }
        goto(WatchItAgain)
    }
    onResponse<No> {
        if(ratingImportant == true) {
            furhat.say{
                +"If you are keen about Oscar awarded movies, you should definitely check it out."
            }
        }
        else{
            furhat.say{
                +"Even though you are not specifically interested in Oscar awarded movies,"
                +"you should definitely check it out."
            }
        }
        goto(WatchItAgain)
    }
}

val WatchItAgain: State = state(Parent){
    onEntry {
        if(userseenMostPopularMovie == true)
        {
            furhat.ask("Would you watch it again?")
        }
        else{
            furhat.ask("Would you be interested in watching this movie in the future?")
        }
    }
    onResponse<Yes>{
        goto(WhereToWatch)
    }
    onResponse<No> {
        goto(RecommendMeSomething)
    }
}

val WhereToWatch: State = state(Parent){
    onEntry {
        furhat.ask("Would you rather watch it at home or in the cinema?", timeout = 6000)
    }
    onResponse<Home> {
        furhat.say("I watched it at home after it came out and it was a nice experience.")
        goto(RecommendMeSomething)
    }
    onResponse<Cinema> {
        furhat.say("I watched in the cinema when it came out. I had a very good time.")
        goto(RecommendMeSomething)
    }
    onNoResponse {
        furhat.ask("Would you watch it at home or in the cinema?")
    }
}

val RecommendMeSomething: State = state(Parent) {
    onEntry {
        furhat.ask{
            +"Now onto the next question."
            +delay(200)
            +"Could you recommend me a movie?"
            +"It can be an $favouriteGenre movie or any other genre."
        }
    }

    onResponse<Yes> {
        furhat.ask("Which one?")
    }

    onResponse{
        goto(RecommendationState)
    }
    onNoResponse {
        furhat.ask("Do you have a movie recommendation for me?")
    }
}

val RecommendationState: State = state(Parent){
    onEntry{
        furhat.say{
            +"I'm not sure I've seen it"
            +delay(200)
        }
        furhat.ask("What is the plot of the movie?", timeout = 10000)
    }
    onResponse {
        goto(GiveMeRecommendation)
    }
    onNoResponse {
        furhat.ask("What happens in the movie?", timeout = 10000)
    }
}

val GiveMeRecommendation: State = state(Parent){
    onEntry {
        furhat.ask("And what did you like about it?", timeout = 10000)
    }
    onResponse{
        furhat.say{
            +"Interesting."
            +delay(200)
            +"Thanks for this recommendation."
            +delay(200)
            +"Onto the final question."
        }
        goto(LastQuestion)
    }
}

val LastQuestion: State = state(Parent){
    onEntry {
        furhat.say("What are your absolute favorite movies?")
        furhat.ask("If you had to choose a couple, that you have to watch for the rest of your life, which ones would those be?", timeout = 20000)
    }
    onResponse{
        goto(EndInterAction)
    }
    onNoResponse {
        furhat.ask("What are the best movies ever made?", timeout = 10000)
    }
}

val EndInterAction: State = state(Parent) {
    onEntry {
        furhat.say{
            +"Thank you so much for taking the time for this interview."
            +"It is very much appreciated."
            +delay(200)
            +"Have a nice day."
        }
        goto(Idle)
    }
}
