package ru.dsvusial.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import ru.dsvusial.guessinggame.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!


    val words = listOf("Android", "Activity", "Fragment")
    var secretWordDisplay = ""
    var secretWord = words.random().uppercase()
    var correctGuesses = ""
    var incorrectGuesses = ""
    var livesLeft = 8

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        secretWordDisplay = deriveSecretWordDisplay()
        updateScreen()
        binding.guessButton.setOnClickListener {
            makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null
            updateScreen()
            if (isWon() || isLost()) {
                val action = GameFragmentDirections
                    .actionGameFragmentToResultFragment(wonLostMessage())
                view.findNavController().navigate(action)
            }
        }
        return view
    }

    private fun wonLostMessage(): String {
        var message = ""
        if (isWon()) message = "You won!"
        else if (isLost()) message = "You lost!"
        message += "The word was $secretWord."
        return message
    }

    private fun isWon() = secretWord.equals(secretWordDisplay, true)
    fun isLost() = livesLeft <= 0


    private fun updateScreen() {
        binding.word.text = secretWordDisplay
        binding.lives.text = "You have $livesLeft lives left"
        binding.incorrectGuesses.text = "Incorrect guesses : $incorrectGuesses"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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


}
