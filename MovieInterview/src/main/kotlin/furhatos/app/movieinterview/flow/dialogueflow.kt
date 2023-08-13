package furhatos.app.movieinterview.flow

import furhatos.app.movieinterview.flow.main.*
import furhatos.app.movieinterview.setting.*
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val LastMovieWatched: State = state(Parent) {
    onEntry {
        furhat.say{
            +"Alright!"
            +delay(100)
            +"Let’s start with a light question."
            +delay(100)
        }
        furhat.ask("What was the last movie you watched in the cinema?", timeout = 5000)
    }
    onResponse<WhatQuestion>{
        reentry()
    }
    onResponse {
        goto(RecommendMovie)
    }
    onReentry {
        furhat.ask("What was the last movie you have seen in the cinema?", timeout = 5000)
    }
}

val RecommendMovie: State = state(Parent) {
    onEntry {
        furhat.ask("Would you recommend this movie to others?", timeout = 3000)
    }
    onResponse<Yes> {
        goto(RememberMovieState)
    }
    onResponse<No>{
        goto(WhyNotLikeMovie)
    }
    onResponse<Uncertain>{
        goto(UncertainMovie)
    }
    onResponse<WhatQuestion>{
        reentry()
    }
    onReentry {
        furhat.ask("Would you recommend it to others?", timeout = 3000)
    }
}

val WhyNotLikeMovie: State = state(Parent){
    onEntry {
        furhat.ask("Why not? Was there anything specific you did not like about it?", timeout = 5000)
    }
    onResponse<WhatQuestion>{
        reentry()
    }
    onResponse {
        goto(RememberMovieState)
    }
    onReentry {
        furhat.ask("What did you not like about it", timeout = 5000)
    }
}

val UncertainMovie: State = state(Parent){
    onEntry {
        furhat.ask("Why are you uncertain about it?", timeout = 5000)
    }
    onResponse<WhatQuestion>{
        reentry()
    }
    onResponse {
        goto(RememberMovieState)
    }
    onReentry {
        furhat.ask("What makes you hesitate", timeout = 5000)
    }
}

val RememberMovieState: State = state(Parent) {
    onEntry {
        furhat.say {
            +"Going back in time a little."
            +delay(100)
        }
        furhat.ask("Do you remember the first time you went to the cinema?", timeout = 5000)
    }
    onResponse<No>{
        furhat.say{
            +"Understandable. I only remember pieces."
            +delay(200)
        }
        furhat.say()
        goto(CinemaNumbers)
    }
    onResponse<Yes>{
        goto(WhatDoYouRemember)
    }
    onResponse<WhatQuestion>{
        reentry()
    }
    onReentry {
        furhat.ask("Do you remember your first cinema experience?", timeout = 5000)
    }
}

val WhatDoYouRemember: State = state(Parent) {
    onEntry {
        furhat.ask("What do you still remember about it? Like the movie, the cinema or the year", timeout = 5000)
    }
    onResponse<WhatQuestion>{
        reentry()
    }
    onResponse {
        goto(CinemaNumbers)
    }
    onReentry {
        furhat.ask("What do you remember about your first cinema experience?", timeout = 5000)
    }
}

val CinemaNumbers: State = state(Parent) {
    onEntry {
        furhat.say{
            +"Let us move on to the next topic."
            +delay(200)
            +"According to the research we did for this Interview,"
            +"Last year the average German only went to see one movie in the cinema."
            +delay(100)
        }
        furhat.ask("How about you? How often do you go to the cinema per year?", timeout = 5000)
    }
    onResponse<WhatQuestion>{
        reentry()
    }
    onResponse{
        goto(IsCinemaDying)
    }
    onReentry {
        furhat.ask("How often do you go to the cinema?", timeout = 5000)
    }
}

val IsCinemaDying: State = state(Parent){
    onEntry {
        furhat.say{
            + "Me too."
            +delay(300)
            + "The average number of visits to the cinema has been declining in Germany."
            + "If we look back in time, in 2002 there were still two visits per year on average."
            + "Double of what it was last year"
            +delay(200)
        }
        furhat.ask("Do you think that this decline has to do with the rise of streaming services?", timeout = 5000)
    }
    onResponse<WhatQuestion>{
        reentry()
    }
    onResponse{
        goto(WhyCinemaDying)
    }
    onReentry {
        furhat.ask(
            "Do you think that there is a correlation between the declining cinema numbers and streaming services?",
            timeout = 5000)
    }
}

val HomeOrCinema: State = state(Parent){
    onEntry {
        furhat.ask(
            "What about you? What is your preference? Do you rather watch movies at home or at the cinema?",
            timeout = 3000)
    }
    onResponse<Cinema>{
        furhat.say{
            +"Right,"
            +delay(100)
            +"I guess it's just a different feeling when you get to see movies on the big screen."
        }
        goto(PrepareGenreState)
    }
    onResponse<Home> {
        furhat.say{
            +"Right,"
            +delay(100)
            +"I guess it is more comfortable than going to the cinema."
        }
        goto(PrepareGenreState)
    }
    onResponse<Both> {
        furhat.say{
            +"I agree,"
            +delay(100)
            +"I like watching the newest movies in the cinema, but I enjoy movie nights at home much more."
        }
        goto(PrepareGenreState)
    }
    onResponse<WhatQuestion>{
        reentry()
    }
    onReentry {
        furhat.ask(
            "Do you prefer watching movies at home or at the cinema?",
            timeout = 3000)
    }
}

val WhyCinemaDying: State = state(Parent){
    onEntry {
        furhat.ask("Could you elaborate on that?", timeout = 5000)
    }
    onResponse<WhatQuestion>{
        reentry()
    }
    onResponse{
        goto(HomeOrCinema)
    }
    onReentry { furhat.ask("Could you go into more detail?", timeout = 5000) }
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
        furhat.ask(
            "If you want to watch something, is it important to you that it received good ratings or even was awarded an Oscar?",
            timeout = 3000)
    }
    onResponse<Yes> {
        ratingImportant = true
        goto(AskGenreState)
    }
    onResponse<No> {
        ratingImportant = false
        goto(AskGenreState)
    }
    onResponse<WhatQuestion>{
        reentry()
    }
    onReentry {
        furhat.ask("Is it important to you that a movie was awarded an Oscar or has a good rating?", timeout = 3000)
    }
}

val AskGenreState: State = state(Parent) {
    onEntry {
        if(ratingImportant == true) {
            furhat.say {
                +"To me it is not that important, but I usually have at least a look at the Oscar nominations."
                +delay(300)
                +"Next question."
            }
        }
        else {furhat.say{
            +"I am not keen on it either, but have a look at the Oscar nominations."
            +delay(300)
            +"Next question."}
        }
        furhat.ask("If you had to decide for just one: What would be your favourite movie genre?", timeout = 5000)
    }

    // Since we only allow GenreIntent as an answer, this secures that getMoviesByGenre will have a return value
    onResponse<GenreIntent>{
        favouriteGenre = it.intent.genre.toString()
        filmfromGenre = getWorstMovieByGenre(favouriteGenre)
        furhat.say("I like $favouriteGenre movies as well.")
        goto(SeenMovieState)
    }
    onResponse<WhatQuestion>{
        reentry()
    }
    onResponse{
        furhat.say{
            +"I am not sure I understand."
            +delay(100)
            +"I am familiar with the following genres:"
            + delay(100)
            + Genres().optionsToText()
        }
        furhat.ask("Which one of these would be your favourite genre?", timeout = 5000)
    }
    onReentry {
        furhat.ask("What is your favourite movie genre?", timeout = 5000)
    }
}

val SeenMovieState: State = state(Parent) {
    onEntry {
        furhat.ask("Have you seen $filmfromGenre?", timeout = 3000)
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
                +delay(100)
            }
        }
        else
            furhat.say {
                +"Well, that makes sense, since it is the lowest rated $favouriteGenre movie on IMDB at the moment."
                +delay(200)
            }
        goto(HighestRatedOfGenre)
    }
    onResponse<WhatQuestion>{
        reentry()
    }
    onReentry {
        furhat.ask("Have you ever seen $filmfromGenre?", timeout = 3000)
    }
}

val HighestRatedOfGenre: State = state(Parent) {
    onEntry {
        filmfromGenre = getBestMovieByGenre(favouriteGenre)
        furhat.ask("How about $filmfromGenre, have you seen it?", timeout = 3000)
    }
    onResponse<Yes> {
        userseenMostPopularMovie = true
        if(ratingImportant == true) {
            furhat.say("I thought so, it is an Oscar nominated movie.")
        }
        else{
            furhat.say("It is an Oscar nominated movie after all.")
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
                +"I would recommend watching it."
            }
        }
        goto(WatchItAgain)
    }
    onResponse<WhatQuestion>{
        reentry()
    }
    onReentry {
        filmfromGenre = getBestMovieByGenre(favouriteGenre)
        furhat.ask("Have you ever seen $filmfromGenre?", timeout = 3000)
    }
}

val WatchItAgain: State = state(Parent){
    onEntry {
        if(userseenMostPopularMovie == true)
        {
            furhat.ask("Would you watch it again?", timeout = 3000)
        }
        else{
            furhat.ask("Would you be interested in watching this movie in the future?", timeout = 3000)
        }
    }
    onResponse<Yes>{
        goto(WhereToWatch)
    }
    onResponse<No> {
        goto(RecommendMeSomething)
    }
    onResponse<Uncertain> {
        goto(RecommendMeSomething)
    }
    onResponse<WhatQuestion>{
        reentry()
    }
    onReentry {
        if(userseenMostPopularMovie == true)
        {
            furhat.ask("Would you consider watching it again?", timeout = 3000)
        }
        else{
            furhat.ask("Would you be interested in this movie?", timeout = 3000)
        }
    }
}

val WhereToWatch: State = state(Parent){
    onEntry {
        furhat.ask("Would you rather watch it at home or in the cinema?", timeout = 3000)
    }
    onResponse<Home> {
        furhat.say{
            +"I watched it at home after it came out and it was a nice experience."
            +delay(200)
        }
        goto(RecommendMeSomething)
    }
    onResponse<Cinema> {
        furhat.say{
            +"I watched in the cinema when it came out. I had a very good time."
            +delay(200)
        }
        goto(RecommendMeSomething)
    }
    onResponse<WhatQuestion>{
        reentry()
    }
    onReentry {
        furhat.ask("Would you watch it at home or in the cinema?", timeout = 3000)
    }
}

val RecommendMeSomething: State = state(Parent) {
    onEntry {
        furhat.say{
            +"Now onto the next question."
            +delay(200)
        }
        furhat.ask(
            "Could you recommend me a movie? It can be a $favouriteGenre movie or any other genre.",
            timeout = 5000)
    }
    onResponse<Yes> {
        furhat.ask("Which one?")
    }
    onResponse<WhatQuestion>{
        reentry()
    }
    onResponse{
        goto(RecommendationState)
    }
    onReentry {
        furhat.ask("What movie could you recommend?", timeout = 5000)
    }
}

val RecommendationState: State = state(Parent){
    onEntry{
        furhat.say{
            +"I'm not sure I've seen it"
            +delay(100)
        }
        furhat.ask("What is the plot of the movie?", timeout = 5000)
    }
    onResponse<WhatQuestion>{
        reentry()
    }
    onResponse {
        goto(GiveMeRecommendation)
    }
    onReentry {
        furhat.ask("What happens in the movie?", timeout = 5000)
    }
}

val GiveMeRecommendation: State = state(Parent){
    onEntry {
        furhat.ask("And what did you like about it?", timeout = 5000)
    }
    onResponse<WhatQuestion>{
        reentry()
    }
    onResponse{
        furhat.say{
            +"Interesting."
            +delay(100)
            +"Thanks for this recommendation."
            +delay(200)
            +"Onto the final question."
        }
        goto(LastQuestion)
    }
    onReentry {
        furhat.ask("What did you enjoy about the movie?", timeout = 5000)
    }
}

val LastQuestion: State = state(Parent){
    onEntry {
        furhat.say("What are your absolute favorite movies?")
        furhat.ask("If you had to choose a couple, that you have to watch for the rest of your life, which ones would those be?",
            timeout = 5000)
    }
    onResponse<WhatQuestion>{
        reentry()
    }
    onResponse{
        goto(EndInterAction)
    }
    onReentry {
        furhat.ask("What are your favorite movies?", timeout = 5000)
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
