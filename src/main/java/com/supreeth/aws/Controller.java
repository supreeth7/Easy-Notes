package com.supreeth.aws;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Controller {

	@Autowired
	private NotesRepo repo;

	@GetMapping("/notes")
	public List<Note> getNotes() {
		return repo.findAll();
	}

	@PostMapping("/notes")
	public Note createNote(@Valid @RequestBody Note note) {
		return repo.save(note);
	}

	@GetMapping("/notes/{id}")
	public Note getNotebyId(@PathVariable(value = "id") Long noteId) {
		return repo.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
	}

	@PutMapping("/notes/{id}")
	public Note updateNote(@PathVariable(value = "id") Long noteId, @Valid @RequestBody Note noteDetails) {

		Note note = repo.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

		note.setTitle(noteDetails.getTitle());
		note.setContent(noteDetails.getContent());

		Note updatedNote = repo.save(note);
		return updatedNote;
	}

	@DeleteMapping("/notes/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId) {
		Note note = repo.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

		repo.delete(note);

		return ResponseEntity.ok().build();
	}

	public Controller() {
		// TODO Auto-generated constructor stub
	}

}
