package com.example.notetask.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.notetask.R
import com.example.notetask.databinding.FragmentAddNoteBinding
import com.example.notetask.datamodel.Note
import com.example.notetask.sqlhelper.NotesSqlDatabaseHelper


class AddNoteFragment : Fragment() {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var notesSqlDatabaseHelper: NotesSqlDatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_add_note, container, false)
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesSqlDatabaseHelper = NotesSqlDatabaseHelper(requireContext())

        binding.saveBttton.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        val title = binding.tittleeditText.text.toString()
        val content = binding.contenteditText.text.toString()

        if (title.isNotBlank() && content.isNotBlank()) {
            val note = Note(0, title, content)
            notesSqlDatabaseHelper.insertNotes(note)
//            parentFragmentManager.popBackStack() // Close fragment after saving

            parentFragmentManager.beginTransaction()
                .replace(R.id.FragmentContainerView, NoteFragment())
//                .addToBackStack(null)  // This adds the transaction to the back stack
                .commit()

//            parentFragmentManager.popBackStack()
            Toast.makeText(requireContext(), "Note Added Successfully", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }

}