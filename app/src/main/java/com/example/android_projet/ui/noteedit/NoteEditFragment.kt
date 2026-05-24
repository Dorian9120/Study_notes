package com.example.android_projet.ui.noteedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import android.widget.ArrayAdapter
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.android_projet.NoteApplication
import com.example.android_projet.R
import com.example.android_projet.databinding.FragmentNoteEditBinding
import com.example.android_projet.ui.NoteViewModelFactory

class NoteEditFragment : Fragment() {

    private var _binding: FragmentNoteEditBinding? = null
    private val binding get() = _binding!!

    private val noteId by lazy { arguments?.getLong("noteId", -1L) ?: -1L }

    private val viewModel: NoteEditViewModel by viewModels {
        NoteViewModelFactory((requireActivity().application as NoteApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadNote(noteId)
        observeViewModel()
        setupSaveButton()
        viewModel.navigateBack.observe(viewLifecycleOwner) { shouldNavigate ->
            if (shouldNavigate) {
                viewModel.onNavigatedBack()
                findNavController().popBackStack()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.note.observe(viewLifecycleOwner) { note ->
            note ?: return@observe
            binding.editTextTitle.setText(note.title)
            binding.editTextModule.setText(note.module)
            binding.editTextContent.setText(note.content)
        }
        viewModel.modules.observe(viewLifecycleOwner) { modules ->
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                modules
            )
            binding.editTextModule.setAdapter(adapter)
        }
    }

    private fun setupSaveButton() {
        binding.buttonSave.setOnClickListener {
            Log.d("NoteEdit", "Save clicked")
            val title = binding.editTextTitle.text.toString().trim()
            val module = (binding.editTextModule as? MaterialAutoCompleteTextView)?.text?.toString()?.trim()
                ?: binding.editTextModule.text?.toString()?.trim() ?: ""
            Log.d("NoteEdit", "title=$title module=$module")
            val content = binding.editTextContent.text.toString().trim()

            var valid = true
            if (title.isEmpty()) {
                binding.textInputLayoutTitle.error = getString(R.string.error_title_required)
                valid = false
            } else {
                binding.textInputLayoutTitle.error = null
            }
            if (module.isEmpty()) {
                binding.textInputLayoutModule.error = getString(R.string.error_module_required)
                valid = false
            } else {
                binding.textInputLayoutModule.error = null
            }
            if (!valid) return@setOnClickListener

            Log.d("NoteEdit", "Calling saveNote")
            viewModel.saveNote(title, content, module)
            Log.d("NoteEdit", "saveNote returned")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
