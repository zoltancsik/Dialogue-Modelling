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
        furhat.ask("Do you remember what the last movie you watched in cinema was?")
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
        furhat.ask("Why not? Was there a specific thing you didnt like about it?")
    }
    onResponse {
        goto(RememberMovieState)
    }
}

val RememberMovieState: State = state(Parent) {
    onEntry {
        furhat.say("I see. Moving on to the next question.")
        furhat.ask("Do you remember the first time you went to the cinema?", timeout = 10000)
    }
    onResponse<No>{
        furhat.say{
            +"That makes sense, I also don't remember."
            +"Nowadays I try to go at least once a month with my friends"
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
        furhat.say("What do you remember about it?")
        furhat.ask("Do you still know which cinema it was? and which movie?", timeout = 10000)
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
                    +"According to the research we did for this Interview,"
                    +"Last year the average person only went to see 1 movie in the cinema."
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
            + "Interesting. Like I said, the numbers keep declining."
            + "If we look back in time, in 2002 there were still 2 visits per year on average."
        }
        furhat.ask("Do you think that this decline has to do with the rise of streaming services?", timeout = 10000)
    }
    onResponse<Yes>{
        furhat.say("Well based on the numbers, they definitely play a role")
        goto(HomeOrCinema)
    }
    onResponse<No> {
        furhat.say("Well, if we look at the numbers, they might play a role in this.")
        goto(HomeOrCinema)
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
        furhat.ask("Why do you think that?")
    }
    onResponse{
        goto(PrepareGenreState)
    }
}

val PrepareGenreState: State = state(Parent){
    onEntry {
        furhat.say {
            +"Alright."
            +delay(100)
            +"Now let's talk a bit about your habits when it comes to watching movies."
            +delay(100)
        }
        furhat.ask("If you want to watch something, is it important for you that it has good ratings?")
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
        }
        goto(HighestRatedOfGenre)
    }
    onResponse<No>{
        if(ratingImportant == true) {
            furhat.say {
                +"Well,"
                +"It is the lowest rated $favouriteGenre movie on IMDB at the moment."
                +"Since you care about ratings, it makes sense that you haven't seen it yet"
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
            furhat.say("Yeah I thought so, it is the highest rated $favouriteGenre movie to date.")
            userseenMostPopularMovie = true
        }
        else{
            furhat.say{
                +"Interesting. It is the best rated $favouriteGenre movie to date"
            }
        }
        goto(WatchItAgain)
    }
    onResponse<No> {
        if(ratingImportant == true) {
            furhat.say{
                +"Well, if ratings are important to you, you should definitely check it out."
                +"It is the best rated $favouriteGenre movie to date"
            }
        }
        else{
            furhat.say{
                +"Well, I know you said you are not that interested in ratings,"
                +"but it is the best rated $favouriteGenre movie to date."
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
        furhat.ask("Right, would you rather watch it at home or in the cinema?", timeout = 6000)
    }
    onResponse<Home> {
        furhat.say("I see, I watched it at home after it came out and it was a nice experience.")
        goto(RecommendMeSomething)
    }
    onResponse<Cinema> {
        furhat.say("I see, I watched in the cinema when it came out. It was amazing.")
        goto(RecommendMeSomething)
    }
    onNoResponse {
        furhat.ask("Would you watch it at home or in the cinema?")
    }
}

val RecommendMeSomething: State = state(Parent) {
    onEntry {
        furhat.ask{
            +"Now onto another question."
            +"Do you think you could give me a movie recommendation?"
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
        furhat.ask("Do you have a movie recommendtaion for me?")
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
        furhat.ask("What is the plot of the movie?", timeout = 10000)
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
            +"Now to the final question."
        }
        goto(LastQuestion)
    }
}

val LastQuestion: State = state(Parent){
    onEntry {
        furhat.say("What are your absolute favourite movies?")
        furhat.ask("If you had to choose a couple, that you have to watch for the rest of your life, which ones would those be?", timeout = 20000)
    }
    onResponse{
        goto(EndInterAction)
    }
    onNoResponse {
        furhat.ask("What did you particularly like about that movie", timeout = 10000)
    }
}

val EndInterAction: State = state(Parent) {
    onEntry {
        furhat.say("Thank you so much for taking the time for this interview")
        furhat.say("Have a nice day")
        goto(Idle)
    }
}

