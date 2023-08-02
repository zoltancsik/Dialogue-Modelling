package furhatos.app.movieinterview.flow

import furhatos.flow.kotlin.*

val AskMovieState: State = state(Parent) {
    onEntry {
        furhat.ask("What was the last movie you watched?")
    }
    onResponse {
        goto(RememberMovieState)
    }
    onNoResponse {
        furhat.ask("The last movie you have watched, what was it?")
    }
}

val RememberMovieState: State = state(Parent) {
    onEntry {
        furhat.ask("What do you remember about it?")
    }
    onResponse{
        goto(RecommendMovieState)
    }
    onNoResponse {
        furhat.ask("What was memorable about the movie?")
    }
}

val RecommendMovieState: State = state(Parent) {
    onEntry {
        furhat.ask("Would you recommend this movie?")
    }
    onResponse{
        furhat.ask("Why is that?")
    }
    onNoResponse {
        furhat.ask("Would you recommend this movie?")
    }
}