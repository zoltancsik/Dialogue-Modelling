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
            +"It won't take too long."
            +delay(100)
            +"I would like to ask you some questions about cinema and movies."
            +"The interview is divided into two parts."
            +"In the first half, I will ask you about cinema in general."
            +"In the second part, I will focus a bit more on your personal preferences."
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