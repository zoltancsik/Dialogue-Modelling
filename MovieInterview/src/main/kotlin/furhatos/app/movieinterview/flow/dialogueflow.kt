package furhatos.app.movieinterview.flow

import furhatos.app.movieinterview.flow.main.*
import furhatos.app.movieinterview.setting.*
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
    onReentry {
        furhat.ask("What is your favourite genre?")
    }

    // Since we only allow GenreIntent as an answer, this secures that getMoviesByGenre will have a return value
    onResponse<GenreIntent>{
        favouriteGenre = it.intent.genre.toString()
        filmfromGenre = getMovieByGenre(favouriteGenre)

        furhat.say("Oh, like $filmfromGenre?")
        goto(SeenMovieState)
    }

    onNoResponse {
        furhat.say{
            +"I am familiar with the following genres:"
            + delay(200)
            + Genres().optionsToText()
        }
        furhat.ask("What would be your favourite genre?")
    }

    onResponseFailed{
        reentry()
    }
}

val SeenMovieState: State = state(Parent) {
    onEntry {
        furhat.ask("Have you seen $filmfromGenre?")
    }

    onResponse<Yes>{
        furhat.say{
            +"I think..."
            +delay(200)
            +"i watched it in the cinema with friends when it came out."
            +delay(200)
        }
        furhat.ask("Where did you watch it for the first time?")
    }

    onResponse<No>{
        val movieName = filmfromGenre.toString()
        val plot = getplotbyMovie(movieName)
        furhat.say{
            +"It's a movie about $plot" // gets a specific description from intents
            +"I enjoyed watching it."
            +"It certainly is a recommendation from me."
            +delay(200)
        }
        filmfromGenre = getMovieByGenre(favouriteGenre)
        furhat.ask("How about $filmfromGenre? Have you seen it?")
    }
    onNoResponse {
        furhat.ask("Have you seen $filmfromGenre?")
    }
}


