package com.msf.trivia


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.msf.trivia.databinding.FragmentGameBinding

/**
 * A simple [Fragment] subclass.
 *
 */
class GameFragment : Fragment() {

    private val questions: MutableList<Question> = mutableListOf(
        Question(text = "What is Android Jetpack?",
            answers = listOf("all of these", "tools", "documentation", "libraries")),
        Question(text = "Base class for Layout?",
            answers = listOf("ViewGroup", "ViewSet", "ViewCollection", "ViewRoot")),
        Question(text = "Layout for complex Screens?",
            answers = listOf("ConstraintLayout", "GridLayout", "LinearLayout", "FrameLayout")),
        Question(text = "Pushing structured data into a Layout?",
            answers = listOf("Data Binding", "Data Pushing", "Set Text", "OnClick")),
        Question(text = "Inflate layout in fragments?",
            answers = listOf("onCreateView", "onActivityCreated", "onCreateLayout", "onInflateLayout")),
        Question(text = "Build system for Android?",
            answers = listOf("Gradle", "Graddle", "Grodle", "Groyle")),
        Question(text = "Android vector format?",
            answers = listOf("VectorDrawable", "AndroidVectorDrawable", "DrawableVector", "AndroidVector")),
        Question(text = "Android Navigation Component?",
            answers = listOf("NavController", "NavCentral", "NavMaster", "NavSwitcher")),
        Question(text = "Registers app with launcher?",
            answers = listOf("intent-filter", "app-registry", "launcher-registry", "app-launcher")),
        Question(text = "Mark a layout for Data Binding?",
            answers = listOf("<layout>", "<binding>", "<data-binding>", "<dbinding>"))
    )

    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = Math.min((questions.size + 1) / 2, 3)
    private val NOT_SELECTED_ID = -1

    private lateinit var binding: FragmentGameBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_game, container, false)
        randomizeQuestions()
        binding.question = this.currentQuestion
        // Set the onClickListener for the submitButton
        binding.submitButton.setOnClickListener { view: View ->
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId
            // Do nothing if nothing is checked (id == -1)
            if (-1 != checkedId) {
                var answerIndex = 0
                when (checkedId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                    R.id.thirdAnswerRadioButton -> answerIndex = 2
                    R.id.fourthAnswerRadioButton -> answerIndex = 3
                }
                // The first answer in the original question is always the correct one, so if our
                // answer matches, we have the correct answer.
                if (answers[answerIndex] == currentQuestion.answers[0]) {
                    questionIndex++
                    // Advance to the next question
                    if (questionIndex < numQuestions) {
                        currentQuestion = questions[questionIndex]
                        setQuestion()
                        binding.invalidateAll()
                    } else {
                        // We've won!  Navigate to the gameWonFragment.
                        view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment(numQuestions,questionIndex))
                    }
                } else {
                    // Game over! A wrong answer sends us to the gameOverFragment.
                    view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment())
                }
            }
        }
        return binding.root
    }

    private fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }

    private fun setQuestion() {
        currentQuestion = questions[questionIndex]
        answers = currentQuestion.answers.toMutableList()
        answers.shuffle()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_android_trivia_question, questionIndex + 1, numQuestions)
    }
}
