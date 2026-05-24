package com.example.android_projet.ui.notedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.android_projet.NoteApplication
import com.example.android_projet.R
import com.example.android_projet.databinding.FragmentNoteDetailBinding
import com.example.android_projet.ui.NoteViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteDetailFragment : Fragment() {

    private var _binding: FragmentNoteDetailBinding? = null
    private val binding get() = _binding!!

    private val noteId by lazy { requireArguments().getLong("noteId") }

    private val viewModel: NoteDetailViewModel by viewModels {
        NoteViewModelFactory((requireActivity().application as NoteApplication).repository)
    }

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadNote(noteId)
        observeViewModel()
        setupMenu()
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
            binding.textViewTitle.text = note.title
            binding.chipModule.text = note.module
            binding.textViewContent.text = note.content
            binding.textViewCreatedAt.text =
                getString(R.string.created_at, dateFormat.format(Date(note.createdAt)))
            binding.textViewUpdatedAt.text =
                getString(R.string.updated_at, dateFormat.format(Date(note.updatedAt)))
            binding.buttonFavorite.setIconResource(
                if (note.isFavorite) android.R.drawable.btn_star_big_on
                else android.R.drawable.btn_star_big_off
            )
            binding.buttonFavorite.text =
                if (note.isFavorite) getString(R.string.remove_favorite)
                else getString(R.string.add_favorite)
        }
        binding.buttonFavorite.setOnClickListener { viewModel.toggleFavorite() }
    }

    private fun setupMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_note_detail, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_edit -> {
                        findNavController().navigate(
                            R.id.actionNoteDetailToNoteEdit,
                            bundleOf("noteId" to noteId)
                        )
                        true
                    }
                    R.id.action_delete -> {
                        viewModel.deleteNote()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
