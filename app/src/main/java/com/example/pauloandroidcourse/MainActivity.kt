package com.example.pauloandroidcourse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var index = 0
        var question : ArrayList<Question> =  ArrayList<Question>()
        question.add(Question(R.string.question_amendments,false))
        question.add(Question(R.string.question_constitution,true))
        question.add(Question(R.string.question_declaration,true))
        question.add(Question(R.string.question_independence_rights,true))
        question.add(Question(R.string.question_religion,true))
        question.add(Question(R.string.question_government,false))
        question.add(Question(R.string.question_government_feds,false))
        question.add(Question(R.string.question_government_senators,true))
        var currentQuestion : Question = question[index]


        quote.text = getString(currentQuestion.id)
        trueBtn.setOnClickListener {
            if(currentQuestion.answerTrue)
            Toast.makeText(applicationContext,"True",Toast.LENGTH_SHORT).show()
            false
            Toast.makeText(applicationContext,"False",Toast.LENGTH_SHORT).show()
        }
        falseBtn.setOnClickListener {
            if(!currentQuestion.answerTrue)
                Toast.makeText(applicationContext,"True",Toast.LENGTH_SHORT).show()
            false
            Toast.makeText(applicationContext,"False",Toast.LENGTH_SHORT).show()
        }

        next_btn.setOnClickListener {
            if(index < question.size-1)
            {
                index++
                currentQuestion = question[index]
                quote.text = getString(currentQuestion.id)
            }
            else
                index = 0
        }
    }
}
