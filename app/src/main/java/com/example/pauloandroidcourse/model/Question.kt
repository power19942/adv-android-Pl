package com.example.pauloandroidcourse.model

class Question(var answer: String,var isAnswerTrue:Boolean) {


    override fun toString(): String {
        return "Question{" +
                "answer='" + answer + '\''.toString() +
                ", answerTrue=" + isAnswerTrue +
                '}'.toString()
    }
}