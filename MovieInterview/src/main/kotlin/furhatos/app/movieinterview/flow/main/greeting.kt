package furhatos.app.movieinterview.flow.main

import furhatos.app.movieinterview.flow.*
import furhatos.flow.kotlin.*

val StartInteraction: State = state(Parent) {
    onEntry {
        val greeting = getTimeBasedGreeting()
        val dayTime = getDayTimeBasedOnTime()
        furhat.say {
            +"$greeting."
            +delay(500)
            +"First of all, I would like to thank you for joining me $dayTime and taking the time for this interview."
            +"It won't take too long, don't worry."
            +delay(100)
            +"I would like to ask you to answer some questions."
            +"I divided them into two main parts."
            +"In the first half, I will ask your opinion about movies and cinema in general."
            +"In the second part we will focus a bit more on your personal habits and preferences."
            +delay(500)
            +"During our conversation, feel free to just say whatever is on your mind"
            +delay(500)
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
