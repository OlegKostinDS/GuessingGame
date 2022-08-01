package ru.dsvusial.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
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

        viewModel.incorrectGuesses.observe(viewLifecycleOwner,
            Observer { newValue ->
                binding.incorrectGuesses.text = "Incorrect guesses: $newValue"
            })

        viewModel.livesLeft.observe(viewLifecycleOwner,
            Observer { newValue ->
                binding.lives.text = "You have : $newValue lives left"
            })

        viewModel.secretWordDisplay.observe(viewLifecycleOwner,
            Observer { newValue -> binding.word.text = newValue })

        viewModel.gameOver.observe(viewLifecycleOwner,
        Observer { newValue ->
            if (newValue) {
                val action = GameFragmentDirections
                    .actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                view.findNavController().navigate(action)
            }
        })

        binding.guessButton.setOnClickListener {
            viewModel.makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null




        }
        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
