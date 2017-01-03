package ${basePackage}.api.service;

import ${basePackage}.api.model.Note;

import java.util.List;

public interface NoteService {

    Note get(long id);

    Note save(Note note);

    List<Note> list();

    void delete(long id);

}
