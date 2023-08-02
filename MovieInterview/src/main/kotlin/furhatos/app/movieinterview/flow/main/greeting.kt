package furhatos.app.movieinterview.flow.main

import furhatos.app.movieinterview.flow.*
import furhatos.flow.kotlin.*

val StartInteraction: State = state(Parent) {
    onEntry {
        furhat.say {
            +"Hello! I would like to have a short interview with you."
            +"I will just ask a couple of questions about movies and feel free to say the first thing which comes to mind."
            +delay(2000)
            +"Let’s start with a light question."
            +delay(2000)
        }
        furhat.ask("How many movies do you watch per month on average?")
    }

    onReentry {
        // With this we will end up in an endless loop of furhat not understanding the response
        // Maybe implement a counter that stops the dialogue after 3 reentries?
        furhat.ask("I'm not sure I understood that, could you repeat the number?")
    }

    onResponse {
        val number = it.text.toIntOrNull()
        if (number != null) {
            val moviesYear = number*12
            furhat.say {
                +"That is about $moviesYear movies a year."
                +"Since I have Netflix I probably watch around 2 movies a week. "
                +delay(2000)
            }
            goto(AskMovieState)
        }
        else {
            reentry()
        }
    }

    onNoResponse {
        furhat.ask("Could you give me a number on how many movies you watch per month?")
    }
}
