package furhatos.app.movieinterview.flow.main

import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onUserEnter
import furhatos.flow.kotlin.state
import furhatos.gestures.Gestures

val Idle: State = state {
    onEntry {
        furhat.attendNobody()
        furhat.gesture(Gestures.CloseEyes)
    }

    onUserEnter {
        furhat.attend(it)
        furhat.gesture(Gestures.OpenEyes)
        goto(StartInteraction)
    }

}
