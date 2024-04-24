package com.example.notpad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notpad.databinding.ActivityNotaddactivityBinding

class notaddactivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotaddactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotaddactivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val context = this
        var db = DataHelpers(context)
        binding.btnAddNote.setOnClickListener {
            val title = binding.nametext.text.toString()
            val text = binding.metintext.text.toString()
            if(title.isNotEmpty() && text.isNotEmpty())
            {
                val data = Dataadapter(title,text)
                db.insertdata(data)
                Toast.makeText(this,"Save Unsuccessfully",Toast.LENGTH_LONG).show()
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }else {
                Toast.makeText(this,"Please Fill in the Blanks",Toast.LENGTH_LONG).show()
            }

        }
    }
}