package furhatos.app.movieinterview.flow

import furhatos.app.movieinterview.flow.main.Idle
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures

val Parent: State = state {
    onInterimResponse(endSil = 3000) {
        // Give some feedback, indicating that FurHat is listening.
        random (
            { furhat.gesture(Gestures.Nod) },
            { furhat.gesture(Gestures.Thoughtful) }
        )
    }

    var nomatches = 0
    var silences = 0

    onResponse {
        nomatches++
        when (nomatches)  {
            1 -> furhat.ask("I am sorry. I did not understand. Could you repeat that?")
            2 -> furhat.ask("I am sorry. Could you say that one more time, please?")
            else -> {
                furhat.gesture(Gestures.ExpressSad)
                furhat.say("I am very sorry. I was not able to understand.")
                reentry()
            }
        }
    }

    onNoResponse {
        silences++
        when (silences)  {
            1 -> furhat.ask("I didn't hear anything.")
            2 -> furhat.ask("I still didn't hear you. Could you speak up please?")
            else -> {
                furhat.gesture(Gestures.ExpressSad)
                furhat.say("I am very sorry, but I still didn't hear anything.")
                reentry()
            }
        }
    }

    onUserEnter(instant = true) {
        furhat.glance(it)
    }

    onUserLeave(instant = true) {
        furhat.stopSpeaking()
        goto(Idle)
    }

}
