package furhatos.app.movieinterview.flow.main

import furhatos.app.movieinterview.flow.*
import furhatos.flow.kotlin.*

val StartInteraction: State = state(Parent) {
    onEntry {
       val greeting = getTimeBasedGreeting()
       furhat.say(greeting)
       goto(StartInterview)
    }
}
