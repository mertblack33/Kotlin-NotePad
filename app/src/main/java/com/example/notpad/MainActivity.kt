package com.example.notpad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notpad.databinding.ActivityMainBinding
import kotlin.math.min

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var Title : ArrayList<String>
    private lateinit var Text : ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()
        Title = ArrayList()
        Text = ArrayList()
        adapterupdate()

    }
    fun addnote(view: View)
    {
        val intent = Intent(this,notaddactivity::class.java)
        startActivity(intent)
    }

    fun adapterupdate() {
        val dbhelper = DataHelpers(this)
        val (titlelists, textlists) = dbhelper.readData()

        Title.clear()
        Text.clear()

        for (title in titlelists) {
            val btitle = if (title.length > 15) {
                title.substring(0, min(15, title.length)) + "...."
            } else {
                title
            }
            Title.add(btitle.toString())
        }
        for (text in textlists) {
            if (text.length > 10) {
                Text.add(text.substring(0, 10) + "....")
            } else {
                Text.add(text)
            }
        }

        binding.recy.layoutManager = LinearLayoutManager(this)
        val Ractadapter = RecyAdapter(Title, Text)
        binding.recy.adapter = Ractadapter
    }

    fun datadelete(adapterPosition: Int) {
        if (adapterPosition < 0 || adapterPosition >= Title.size) {
            Toast.makeText(this, "Problem Index", Toast.LENGTH_LONG).show()
            return
        }

        val dbHelper = DataHelpers(this)
        val nameToDelete = Title[adapterPosition]
        val isSuccess = dbHelper.deleteDataByName(nameToDelete)
        if (isSuccess) {
            adapterupdate() // Veri tabanından veriyi sildikten sonra RecyclerView'i güncelle
            Toast.makeText(this, "Clear Success", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Don't Clear", Toast.LENGTH_LONG).show()
        }
    }
}