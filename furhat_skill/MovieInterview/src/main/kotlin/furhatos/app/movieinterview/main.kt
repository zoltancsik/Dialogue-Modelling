package furhatos.app.movieinterview

import furhatos.app.movieinterview.flow.Init
import furhatos.flow.kotlin.Flow
import furhatos.skills.Skill

class MovieinterviewSkill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
