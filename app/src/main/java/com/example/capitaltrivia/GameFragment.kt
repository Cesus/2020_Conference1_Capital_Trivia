package com.example.capitaltrivia

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.capitaltrivia.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    data class Question(
        val text: String,
        val answers: List<String>)

    // Set a list of questions
    // The first answer is correct, later on, the answers will be randomized
    private val questions: MutableList<Question> = mutableListOf(
        Question(text = "What is the capital of Canada?",
            answers = listOf("Ottawa", "Toronto", "Washington", "Beijing")),
        Question(text = "What is the capital of Australia?",
            answers = listOf("Canberra", "Toronto", "Sydney", "None of the above")),
        Question(text = "What is the capital of United Kingdom?",
            answers = listOf("London", "Manchester", "Birmingham", "Lyon")),
        Question(text = "What is the capital of India?",
            answers = listOf("New Delhi", "Mumbai", "Bangkok", "Kuala Lumpur")),
        Question(text = "What is the capital of USA?",
            answers = listOf("Washington", "New York", "Washingdone", "London"))
    )


    var score = 0
    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = questions.size //((questions.size + 1) / 2).coerceAtMost(3)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(
            inflater, R.layout.fragment_game, container, false)



        // Shuffles the questions and sets the question index to the first question.
        randomizeQuestions()

        // Bind this fragment class to the layout
        binding.game = this

        // Set the onClickListener for the submitButton
        binding.submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId
            // -1 means that nothing is checked, so nothing will happen
            if (-1 != checkedId) {
                // correct answer is set to 0, or the first in the array. Anything else is wrong
                var answerIndex = 0
                when (checkedId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                    R.id.thirdAnswerRadioButton -> answerIndex = 2
                    R.id.fourthAnswerRadioButton -> answerIndex = 3
                }
                // First answer is the correct answer, if it matches, score goes up
                if (answers[answerIndex] == currentQuestion.answers[0]) {
                    score++ }

                // Advance to the next question
                questionIndex++
                if (questionIndex < numQuestions) {
                    currentQuestion = questions[questionIndex]
                    setQuestion()
                    binding.invalidateAll()
                } else {
                    // The game is over! Display the final score to the user
                    Toast.makeText(activity, "Your final score is: $score", Toast.LENGTH_LONG).show()
                    // disables submit button (onCreate auto enables it, so it doesnt need to be re-enabled)
                    binding.submitButton.isEnabled = false
                    // Change the title of the support action bar
                    (activity as AppCompatActivity).supportActionBar?.title = "Capital Trivia - Game Over!"
                }

            }
        }
        return binding.root
    }

    // randomize the questions and set the first question
    private fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }

    // Sets the question and randomizes the answers.  This only changes the data, not the UI.
    // Calling invalidateAll on the FragmentGameBinding updates the data.
    private fun setQuestion() {
        currentQuestion = questions[questionIndex]
        // randomize the answers into a copy of the array
        answers = currentQuestion.answers.toMutableList()
        // and shuffle them
        answers.shuffle()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_capital_trivia_question, questionIndex + 1, numQuestions)
    }

}
