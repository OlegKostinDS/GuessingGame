package ru.dsvusial.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import ru.dsvusial.guessinggame.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        val view = binding.root
        updateScreen()
        binding.guessButton.setOnClickListener {
            viewModel.makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null

            updateScreen()


            if (viewModel.isWon() || viewModel.isLost()) {
                val action = GameFragmentDirections
                    .actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                view.findNavController().navigate(action)
            }
        }
        return view
    }

    private fun updateScreen() {
        
        binding.word.text = viewModel.secretWordDisplay
        binding.lives.text =
            "You have ${viewModel.livesLeft} lives left"
        binding.incorrectGuesses.text =
            "Incorrect guesses : ${viewModel.incorrectGuesses}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
