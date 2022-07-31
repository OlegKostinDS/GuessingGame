package ru.dsvusial.guessinggame

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {


    val words = listOf("Android", "Activity", "Fragment")
    var secretWordDisplay = ""
    var secretWord = words.random().uppercase()
    var correctGuesses = ""
    var incorrectGuesses = ""
    var livesLeft = 8

    init {
        secretWordDisplay = deriveSecretWordDisplay()
    }

    private fun deriveSecretWordDisplay(): String {
        var display = ""
        secretWord.forEach {
            display += checkLetter(it.toString())
        }
        return display
    }

    private fun checkLetter(string: String) = when (correctGuesses.contains(string)) {
        true -> string
        false -> "_"

    }

    fun makeGuess(guess: String) {
        if (guess.length == 1) {
            if (secretWord.contains(guess)) {
                correctGuesses += guess
                secretWordDisplay = deriveSecretWordDisplay()
            } else {
                incorrectGuesses += "$guess "
                livesLeft--
            }
        }
    }

     fun wonLostMessage(): String {
        var message = ""
        if (isWon()) message = "You won!"
        else if (isLost()) message = "You lost!"
        message += "The word was $secretWord."
        return message
    }

     fun isWon() = secretWord.equals(secretWordDisplay, true)
    fun isLost() = livesLeft <= 0


}