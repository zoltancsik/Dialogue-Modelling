package furhatos.app.movieinterview.flow

import furhatos.app.movieinterview.setting.recommendsMovie
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val AskMovieState: State = state(Parent) {
    onEntry {
        furhat.ask("What was the last movie you watched?")
    }
    onResponse {
        goto(RememberMovieState)
    }
    onNoResponse {
        furhat.ask("The last movie you have watched, what was it?")
    }
}

val RememberMovieState: State = state(Parent) {
    onEntry {
        furhat.ask("What do you remember about it?")
    }
    onResponse{
        goto(RecommendMovieState)
    }
    onNoResponse {
        furhat.ask("What was memorable about the movie?")
    }
}

val RecommendMovieState: State = state(Parent) {
    onEntry {
        furhat.ask("Would you recommend this movie?")
    }
    onResponse<Yes>{
        recommendsMovie = true
        goto(WhyRecommendMovie)
    }
    onResponse<No>{
        recommendsMovie = false
        goto(WhyRecommendMovie)
    }
    onNoResponse {
        furhat.ask("Would you recommend this movie?")
    }
}

val WhyRecommendMovie: State = state(Parent) {
    onEntry {
        if(recommendsMovie == true){
            furhat.ask("Why would you recommend it?")
        }
        else
            furhat.ask("Why would you not recommend it?")
    }
    onResponse{
        goto(AskGenreState)
    }
    onNoResponse {
        furhat.ask("Would you recommend this movie?")
    }
}

val AskGenreState: State = state(Parent) {
    onEntry {
        furhat.say {
            +"I'll keep that in mind, if I ever get the chance to watch it."
            +delay(2000)
        }
        furhat.ask("If you had to go for just one: What would be your favourite movie genre?")
    }
    onResponse{

    }
    onNoResponse {
        furhat.ask("What would be your favourite genre?")
    }
}