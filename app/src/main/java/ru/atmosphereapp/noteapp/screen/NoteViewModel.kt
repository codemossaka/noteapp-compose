package ru.atmosphereapp.noteapp.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import ru.atmosphereapp.noteapp.data.loadNotes
import ru.atmosphereapp.noteapp.model.Note

class NoteViewModel : ViewModel() {
    private var _noteList = mutableStateListOf<Note>()

    val noteliste: List<Note>
        get() = _noteList

    init {
        _noteList.addAll(loadNotes())
    }

    fun addNote(note: Note) {
        _noteList.add(note)
    }


    fun removeNote(note: Note) {
        _noteList.remove(note)
    }
}