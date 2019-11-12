package com.example.pauloandroidcourse.data

import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.example.pauloandroidcourse.controller.AppController
import com.example.pauloandroidcourse.model.Question

import org.json.JSONArray
import org.json.JSONException

import java.util.ArrayList


class QuestionBank {
    internal var questionArrayList: ArrayList<Question> = ArrayList()
    private val url =
        "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json"

    fun getQuestions(callBack: AnswerListAsyncResponse?): ArrayList<Question> {

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener<JSONArray> {response->

                    for (i in 0 until response.length()) {
                        try {
                            var answer=response.getJSONArray(i).get(0).toString()
                            var isAnswerTrue=response.getJSONArray(i).getBoolean(1)
                            questionArrayList.add(Question(answer,isAnswerTrue))
                            Log.d("JSON",Question(answer,isAnswerTrue).toString())

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                    }
                    if (null != callBack) callBack!!.processFinished(questionArrayList)


            },Response.ErrorListener {

            })
        AppController.instance?.requestQueue?.add(jsonArrayRequest)


        return questionArrayList

    }
}
interface AnswerListAsyncResponse{
    fun processFinished(questions : ArrayList<Question>)
}