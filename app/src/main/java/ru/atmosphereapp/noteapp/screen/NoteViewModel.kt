package ru.atmosphereapp.noteapp.screen

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.atmosphereapp.noteapp.data.loadNotes
import ru.atmosphereapp.noteapp.model.Note
import ru.atmosphereapp.noteapp.repository.NoteRepository
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val noteRepository: NoteRepository) : ViewModel() {
    private val _noteList = MutableStateFlow<List<Note>>(emptyList())

    val notelist: StateFlow<List<Note>>
        get() = _noteList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.getAllNotes().distinctUntilChanged().collect { listOfNotes ->
                if (listOfNotes.isNullOrEmpty()) {
                    Log.d("NoteViewModel", "empty  list")
                } else {
                    _noteList.value = listOfNotes
                }
            }
        }
    }

    suspend fun getAllNotes() = viewModelScope.launch {
        noteRepository.getAllNotes()
    }

    fun addNote(note: Note) = viewModelScope.launch {
        noteRepository.addNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        noteRepository.updateNote(note)
    }

    fun removeNote(note: Note) = viewModelScope.launch {
        noteRepository.deleteNote(note)
    }

    fun removeAllNotes() = viewModelScope.launch {
        noteRepository.deleteAllNotes()
    }
}