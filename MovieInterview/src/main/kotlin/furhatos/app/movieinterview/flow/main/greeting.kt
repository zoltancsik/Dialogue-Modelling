package furhatos.app.movieinterview.flow.main

import furhatos.app.movieinterview.flow.*
import furhatos.flow.kotlin.*

val StartInteraction: State = state(Parent) {
    onEntry {
        furhat.say {
            +"Hello! You have been invited to do a short interview."
            +"In the first part I will ask a couple of questions about cinema and in the second about your movie preference."
            +delay(1000)
            +"Let’s start with a light question."
            +delay(2000)
        }
        furhat.ask("What is the last movie you have watched at the cinema?", timeout = 6000)
    }

    onReentry {
        furhat.ask("You have reentered the greeting state")
    }

    onResponse {
       furhat.ask("Is it a movie you would recommend?")
    }

    onNoResponse {
        furhat.ask("Sorry, my question was, what is the latest movie you saw in the cinema?")
    }
}
