package furhatos.app.movieinterview.flow

import furhatos.app.movieinterview.flow.main.Idle
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures

val Parent: State = state {
    onInterimResponse(endSil = 500) {
        // Give some feedback, indicating that FurHat is listening.
        random (
            { furhat.gesture(Gestures.Nod) },
            { furhat.gesture(Gestures.Thoughtful) }
        )
    }

    onUserEnter(instant = true) {
         furhat.glance(it)
    }

    onUserLeave(instant = true) {
            furhat.stopSpeaking()
            goto(Idle)
    }

}
