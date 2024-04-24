package com.example.notpad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.notpad.databinding.ActivityNotDetailyBinding

class NotDetaily : AppCompatActivity() {
    private lateinit var binding: ActivityNotDetailyBinding
    private lateinit var  Titlelist: ArrayList<String>
    private lateinit var  Textlist: ArrayList<String>
    private var noteIndex : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotDetailyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        Titlelist = ArrayList()
        Textlist = ArrayList()

        loaddata()

    }
    private fun loaddata()
    {
        noteIndex = intent.getIntExtra("noteIndex",0)
        val dbHelper = DataHelpers(this)
        val (titlelists,textlists) = dbHelper.readData()

        Titlelist.addAll(titlelists)
        Textlist.addAll(textlists)

        binding.nametext.setText(Titlelist[noteIndex])
        binding.metintext.setText(Textlist[noteIndex])

    }

    fun deleteNote(view: View)
    {
        if(noteIndex !in 0 until  Titlelist.size)
        {
            Toast.makeText(this,"Problem Index",Toast.LENGTH_LONG).show()
            return
        }
        val dbHelper = DataHelpers(this)
        val nameToName = Titlelist[noteIndex]
        val isSuccess = dbHelper.deleteDataByName(nameToName)
        if(isSuccess)
        {
            Titlelist.removeAt(noteIndex)
            Textlist.removeAt(noteIndex)
            Toast.makeText(this,"Clear Success",Toast.LENGTH_LONG).show()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        else{
            Toast.makeText(this,"Dont Clear",Toast.LENGTH_LONG).show()

        }
    }
    fun updateNote(view: View)
    {
        val dbHelper = DataHelpers(this)
        val nametoupdate = Titlelist[noteIndex]gh
        val newtitle = binding.nametext.editableText.toString()
        val newText = binding.metintext.editableText.toString()

        val isSuccess = dbHelper.updateData(nametoupdate,newtitle,newText)
        if(isSuccess)
        {
            Toast.makeText(this,"Note Update Succsess",Toast.LENGTH_LONG).show()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)

        }
        else{
            Toast.makeText(this,"Dont Update",Toast.LENGTH_LONG).show()
        }
    }
    fun goBack(view: View)
    {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}