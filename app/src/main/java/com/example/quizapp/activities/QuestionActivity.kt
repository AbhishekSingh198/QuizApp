package com.example.quizapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.R
import com.example.quizapp.adapters.OptionAdapter
import com.example.quizapp.models.Questions
import com.example.quizapp.models.Quiz
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_question.*

class QuestionActivity : AppCompatActivity() {
    var quizzes: MutableList<Quiz>? = null
    var questions:MutableMap<String,Questions>?=null
    var index=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        setUpFireStore()
        setUpEventListener()
    }

    private fun setUpEventListener() {
        btnPrevious.setOnClickListener {
            index--
            bindViews()
        }
        btNext.setOnClickListener {
            index++
            bindViews()
        }
        btnSubmit.setOnClickListener {
            Log.d("FINAL QUIZ",questions.toString())
            val intent= Intent(this,ResultActivity::class.java)
            val json=Gson().toJson(quizzes!![0])
            intent.putExtra("QUIZ",json)
            startActivity(intent)
            finish()
        }
    }

    private fun setUpFireStore() {
        val fireStore= FirebaseFirestore.getInstance()
        var date =intent.getStringExtra("DATE")
        if(date!=null) {
            fireStore.collection("quizzes").whereEqualTo("title", date)
                    .get()
                    .addOnSuccessListener {
                        if(it!=null&& !it.isEmpty) {
                            quizzes=it.toObjects(Quiz::class.java)
                            questions= quizzes!![0].questions
                            bindViews()
                        }
                        else{
                            Toast.makeText(this,"No Quiz for selected date",Toast.LENGTH_SHORT).show()
                        }
                    }
        }
    }

    private fun bindViews(){
        btnPrevious.visibility= View.GONE
        btNext.visibility= View.GONE
        btnPrevious.visibility= View.GONE
        if(index==1){
            btNext.visibility=View.VISIBLE
        }
        else if (index== questions!!.size){
            btnSubmit.visibility=View.VISIBLE
            btnPrevious.visibility=View.VISIBLE
        }
        else{
            btnPrevious.visibility= View.VISIBLE
            btNext.visibility= View.VISIBLE
        }
        val question=questions!!["question$index"]
        question?.let {
            description.text = it.description
            val optionAdapter = OptionAdapter(this, it)
            optionList.layoutManager = LinearLayoutManager(this)
            optionList.adapter = optionAdapter
            optionList.setHasFixedSize(true)
        }
    }
}