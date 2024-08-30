package com.example.notetask.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notetask.R
import com.example.notetask.databinding.FragmentNoteBinding
import com.example.notetask.noteAdapter.NotesAdapter
import com.example.notetask.sqlhelper.NotesSqlDatabaseHelper


class NoteFragment : Fragment() {
    private lateinit var binding: FragmentNoteBinding
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var notesSqlDatabaseHelper: NotesSqlDatabaseHelper
    private lateinit var fragmentManager: FragmentManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_note, container, false)

        binding = FragmentNoteBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesSqlDatabaseHelper = NotesSqlDatabaseHelper(requireContext())

        notesAdapter = NotesAdapter(notesSqlDatabaseHelper.getAllNotes(), requireContext())
        binding.RecyclerViewId.layoutManager = LinearLayoutManager(requireContext())
        binding.RecyclerViewId.adapter = notesAdapter

        binding.addButton.setOnClickListener{
            goToFragment(AddNoteFragment())

        }
    }
    private fun goToFragment(fragment: Fragment){

        fragmentManager = (activity as FragmentActivity).supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.FragmentContainerView, fragment)
//            .addToBackStack(null)
            .commit()
    }

    override fun onResume() {
        super.onResume()
        notesAdapter.referesData(notesSqlDatabaseHelper.getAllNotes())
    }

}
