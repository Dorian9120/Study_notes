package com.example.android_projet.ui.notelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_projet.NoteApplication
import com.example.android_projet.R
import com.example.android_projet.data.model.ModuleCount
import com.example.android_projet.databinding.FragmentNoteListBinding
import com.example.android_projet.ui.NoteViewModelFactory
import com.google.android.material.chip.Chip

class NoteListFragment : Fragment() {

    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NoteListViewModel by viewModels {
        NoteViewModelFactory((requireActivity().application as NoteApplication).repository)
    }

    private lateinit var adapter: NoteAdapter
    private var activeModule: String? = null
    private var isFavoritesActive = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearch()
        setupFab()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = NoteAdapter(
            onNoteClick = { note ->
                findNavController().navigate(
                    R.id.actionNoteListToNoteDetail,
                    bundleOf("noteId" to note.id)
                )
            },
            onFavoriteToggle = { note -> viewModel.toggleFavorite(note) }
        )
        binding.recyclerViewNotes.adapter = adapter
        binding.recyclerViewNotes.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.setSearchQuery(newText.orEmpty())
                return true
            }
        })
    }

    private fun setupFab() {
        binding.fabAddNote.setOnClickListener {
            findNavController().navigate(R.id.actionNoteListToNoteEdit)
        }
    }

    private fun observeViewModel() {
        viewModel.notes.observe(viewLifecycleOwner) { notes ->
            adapter.submitList(notes)
            binding.textViewEmpty.visibility = if (notes.isEmpty()) View.VISIBLE else View.GONE
        }
        viewModel.moduleNoteCounts.observe(viewLifecycleOwner) { counts ->
            updateModuleChips(counts)
        }
    }

    private fun updateModuleChips(moduleCounts: List<ModuleCount>) {
        binding.chipGroupModules.removeAllViews()
        binding.chipGroupModules.addView(makeChip(getString(R.string.all_modules), null))
        binding.chipGroupModules.addView(makeChip(getString(R.string.favorites), "FAV"))
        moduleCounts.forEach { mc ->
            binding.chipGroupModules.addView(makeChip("${mc.module} (${mc.count})", mc.module))
        }
    }

    private fun makeChip(label: String, key: String?): Chip {
        return Chip(requireContext()).apply {
            text = label
            isCheckable = true
            isChecked = when {
                key == null -> !isFavoritesActive && activeModule == null
                key == "FAV" -> isFavoritesActive
                else -> !isFavoritesActive && activeModule == key
            }
            setOnClickListener {
                when {
                    key == null -> {
                        isFavoritesActive = false
                        activeModule = null
                        viewModel.setSelectedModule(null)
                        viewModel.setShowFavoritesOnly(false)
                    }
                    key == "FAV" -> {
                        isFavoritesActive = true
                        activeModule = null
                        viewModel.setShowFavoritesOnly(true)
                    }
                    else -> {
                        isFavoritesActive = false
                        activeModule = key
                        viewModel.setSelectedModule(key)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
