package com.example.pauloandroidcourse

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import com.example.pauloandroidcourse.data.AnswerListAsyncResponse
import com.example.pauloandroidcourse.data.QuestionBank
import com.example.pauloandroidcourse.model.Question
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var index = 0
        var currentQuestion:Question
        QuestionBank().getQuestions(object : AnswerListAsyncResponse{
            override fun processFinished(questions: ArrayList<Question>) {
                counterText.text = "$index out ${questions.size}"
                currentQuestion = questions[0]
                questionTxt.text = currentQuestion.answer
                nextBtn.setOnClickListener {
                    if(index + 1 <= questions.size -1)
                        currentQuestion = questions[++index]
                    questionTxt.text = currentQuestion.answer
                    counterText.text = "$index out ${questions.size}"
                }
                prevBtn.setOnClickListener {
                    if(index - 1 >= 0)
                        currentQuestion = questions[--index]
                    questionTxt.text = currentQuestion.answer
                    counterText.text = "$index out ${questions.size}"
                }
                falseBtn.setOnClickListener {
                    if(!currentQuestion.isAnswerTrue)
                    {
                        Toast.makeText(applicationContext,"true :)",Toast.LENGTH_SHORT).show()
                        //currentQuestion = questions[++index]
                        fadeView()
                        questionTxt.text = currentQuestion.answer
                        counterText.text = "$index out ${questions.size}"
                    }
                    else
                    {
                        Toast.makeText(applicationContext,"False :(",Toast.LENGTH_SHORT).show()
                        shakeAnimation()
                        questionTxt.text = currentQuestion.answer
                        counterText.text = "$index out ${questions.size}"
                    }
                }
                trueBtn.setOnClickListener {
                    if(currentQuestion.isAnswerTrue)
                    {
                        Toast.makeText(applicationContext,"true :)",Toast.LENGTH_SHORT).show()
                       // currentQuestion = questions[++index]
                        fadeView()
                        questionTxt.text = currentQuestion.answer
                        counterText.text = "$index out ${questions.size}"
                    }
                    else
                    {
                        Toast.makeText(applicationContext,"False :(",Toast.LENGTH_SHORT).show()
                        shakeAnimation()
                        questionTxt.text = currentQuestion.answer
                        counterText.text = "$index out ${questions.size}"
                    }
                }
            }
        })

    }

    fun fadeView(){
        var alphaAnimation = AlphaAnimation(1.0f,0.0f)
        alphaAnimation.duration = 350
        alphaAnimation.repeatCount = 1
        alphaAnimation.repeatMode = Animation.REVERSE
        cardView.animation = alphaAnimation
        alphaAnimation.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(p0: Animation?) {
                cardView.setCardBackgroundColor(Color.GREEN)
                questionTxt.setTextColor(Color.WHITE)
            }

            override fun onAnimationEnd(p0: Animation?) {
                cardView.setCardBackgroundColor(Color.WHITE)
                questionTxt.setTextColor(Color.BLACK)
            }

            override fun onAnimationStart(p0: Animation?) {
            }
        })
    }

    fun shakeAnimation(){
        var animation = AnimationUtils.loadAnimation(this@MainActivity,R.anim.shake_animation)
        cardView.animation = animation
        animation.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                cardView.setCardBackgroundColor(Color.RED)
                questionTxt.setTextColor(Color.WHITE)
            }

            override fun onAnimationEnd(p0: Animation?) {
                cardView.setCardBackgroundColor(Color.WHITE)
                questionTxt.setTextColor(Color.BLACK)
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }

        })
    }
}

