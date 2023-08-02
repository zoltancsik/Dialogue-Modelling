package furhatos.app.movieinterview.flow

import furhatos.flow.kotlin.*

val AskMovieState: State = state(Parent) {
    onEntry {
        furhat.ask("What was the last movie you watched?")
    }
    onResponse {
        furhat.ask("What do you remember about it?")
    }
    onNoResponse {
        furhat.ask("The last movie you have watched, what was it?")
    }
}