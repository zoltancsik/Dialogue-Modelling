package furhatos.app.movieinterview.flow

import furhatos.app.movieinterview.flow.main.Idle
import furhatos.app.movieinterview.flow.main.Greeting
import furhatos.app.movieinterview.setting.DISTANCE_TO_ENGAGE
import furhatos.app.movieinterview.setting.MAX_NUMBER_OF_USERS
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users

val Init: State = state {
    init {
        /** Set our default interaction parameters */
        users.setSimpleEngagementPolicy(DISTANCE_TO_ENGAGE, MAX_NUMBER_OF_USERS)
    }
    onEntry {
        /** start interaction */
        when {
            users.hasAny() -> {
                furhat.attend(users.random)
                goto(Greeting)
            }
            else -> goto(Idle)
        }
    }

}
