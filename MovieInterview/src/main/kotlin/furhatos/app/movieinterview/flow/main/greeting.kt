package furhatos.app.movieinterview.flow.main

import furhatos.app.movieinterview.flow.*
import furhatos.flow.kotlin.*

val StartInteraction: State = state(Parent) {
    onEntry {
        goto(WhyCinemaDying)
        val greeting = getTimeBasedGreeting()
        furhat.say {
            +"$greeting and thank you for joining me and taking the time for this interview."
            +"It won't take too long, don't worry."
            +"I divided my questions into two main parts."
            +"In the first half, I will ask your opinion about movies and cinema in general."
            +"In the second part we will focus a bit more on your personal habits and preferences."
            +delay(1000)
            +"During our conversation, feel free to just say whatever is on your mind"
        }
        furhat.ask("Let me know if you are ready to start.", timeout = 10000)
    }

    onReentry {
        furhat.ask("Are you ready to start the interview?")
    }

    onResponse<Ready>{
        goto(LastMovieWatched)
    }

    onNoResponse {
        furhat.ask("Let me know if you are ready to start.")
    }
}
