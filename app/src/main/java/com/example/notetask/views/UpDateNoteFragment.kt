package com.example.notetask.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.notetask.R
import com.example.notetask.databinding.FragmentUpDateNoteBinding
import com.example.notetask.datamodel.Note
import com.example.notetask.sqlhelper.NotesSqlDatabaseHelper


class UpDateNoteFragment : Fragment() {

    private var _binding: FragmentUpDateNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var notesSqlDatabaseHelper: NotesSqlDatabaseHelper
    private var noteId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_up_date_note, container, false)

        _binding = FragmentUpDateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesSqlDatabaseHelper = NotesSqlDatabaseHelper(requireContext())

        noteId = arguments?.getInt("note_id") ?: -1
        if (noteId == -1) {
            requireActivity().supportFragmentManager.popBackStack() // Close fragment if noteId is invalid
            return
        }


        val note = notesSqlDatabaseHelper.getNotById(noteId)
        binding.updatetittleeditText.setText(note.title)
        binding.updatecontenteditText.setText(note.content)


        binding.updatesaveBttton.setOnClickListener {
            val newTitle = binding.updatetittleeditText.text.toString()
            val newContent = binding.updatecontenteditText.text.toString()
            if (newTitle.isNotBlank() && newContent.isNotBlank()) {
                val upDateNote = Note(noteId, newTitle, newContent)
                notesSqlDatabaseHelper.upDateDataNote(upDateNote)

                parentFragmentManager.beginTransaction()
                    .replace(R.id.FragmentContainerView, NoteFragment())
                    .addToBackStack(null)  // This adds the transaction to the back stack
                    .commit()

                Toast.makeText(requireContext(), "Changes Saved", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
