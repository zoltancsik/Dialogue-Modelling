package furhatos.app.movieinterview.flow

import furhatos.app.movieinterview.flow.main.Idle
import furhatos.flow.kotlin.*

val Parent: State = state {

    onUserEnter(instant = true) {
         furhat.glance(it)
    }

    onUserLeave(instant = true) {
           goto(Idle)
    }

}
