package com.msf.trivia


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.msf.trivia.databinding.FragmentGameBinding
import com.msf.trivia.databinding.FragmentGameWonBinding


/**
 * A simple [Fragment] subclass.
 *
 */
class GameWonFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_game_won, container, false)
        binding.nextMatchButton.setOnClickListener {
            it.findNavController().navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
        }
        val args  = arguments?.let { GameWonFragmentArgs.fromBundle(it) }
        Toast.makeText(context, "Num questions: ${args!!.numQuestions}, NumCorrect: ${args!!.numCorrect}", Toast.LENGTH_LONG).show()
        return binding.root
    }


}
