package com.example.notetask.noteAdapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.notetask.R

import com.example.notetask.databinding.NotesItemsBinding
import com.example.notetask.datamodel.Note
import com.example.notetask.sqlhelper.NotesSqlDatabaseHelper
import com.example.notetask.views.UpDateNoteFragment

class NotesAdapter(private var note :List<Note>, private var context: Context):
    RecyclerView.Adapter<NotesAdapter.MyViewHolder>() {
        private val db: NotesSqlDatabaseHelper = NotesSqlDatabaseHelper(context)
    inner class MyViewHolder(var binding: NotesItemsBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding = NotesItemsBinding.inflate(LayoutInflater.from(context,),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return note.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.titletextView.text = note.get(position).title
        holder.binding.contenttextView.text = note.get(position).content
        holder.binding.updatebuttton.setOnClickListener {
            // Get the note ID
            val noteId = note[position].id

            // Create a new instance of the fragment
            val fragment = UpDateNoteFragment().apply {
                // Pass data to the fragment using arguments
                arguments = Bundle().apply {
                    putInt("note_id", noteId)
                }
            }

            // Use FragmentManager to begin the transaction
            val activity = holder.itemView.context as AppCompatActivity
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.FragmentContainerView, fragment) // Replace the current fragment with the new one
                .addToBackStack(null) // Optional: Adds the transaction to the back stack
                .commit()
        }


        holder.binding.deleteButton.setOnClickListener {
            db.deleteNote(note[position].id)
            referesData(db.getAllNotes())
            Toast.makeText(holder.itemView.context,"Note Delete Successfully", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun  referesData(newNotes: List<Note>){
        note = newNotes
        notifyDataSetChanged()
    }

}