package com.example.notpad

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notpad.databinding.RecytableBinding

class RecyAdapter(val TitleList: ArrayList<String>,val TextList: ArrayList<String> ) : RecyclerView.Adapter<RecyAdapter.RecyHolder>() {
    inner class RecyHolder(val binding: RecytableBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyHolder {
        val binding = RecytableBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyHolder(binding)
    }

    override fun getItemCount(): Int {
        return TitleList.size
    }

    override fun onBindViewHolder(holder: RecyHolder, position: Int) {
        holder.binding.title.text = TitleList[position]
        holder.binding.nottitle.text = TextList[position]
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, NotDetaily::class.java)
            intent.putExtra("noteindex", position)
            holder.itemView.context.startActivity(intent)
        }
        holder.binding.imageButton2.setOnClickListener {
            (holder.itemView.context as? MainActivity)?.datadelete(holder.adapterPosition)
        }
    }
}