package ${basePackage}.service;

import ${basePackage}.api.model.Note;
import ${basePackage}.api.service.NoteService;
import ${basePackage}.persistence.entity.NoteEntity;
import idx.persistence.repository.Repository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class NoteServiceImpl implements NoteService {

    @Inject
    private Repository<NoteEntity, Long> repository;


    @Override
    public Note get(long id) {
        return convert(repository.get(id));
    }

    @Override
    public Note save(Note note) {
        NoteEntity entity = new NoteEntity();
        if (note.getId() != null) {
            entity = repository.get(note.getId());
        }
        return convert(repository.save(update(entity, note)));
    }

    @Override
    public List<Note> list() {
        List<NoteEntity> list = repository.list();
        return list.stream().map(this::convert).collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        repository.delete(id);
    }

    private Note convert(NoteEntity entity) {
        if (entity == null) {
            return null;
        }
        Note dto = new Note();
        dto.setId(entity.getId());
        dto.setText(entity.getText());
        dto.setCreationDate(entity.getCreationDate());
        dto.setModificationDate(entity.getLastModifiedDate());
        return dto;
    }

    private NoteEntity update(NoteEntity entity, Note dto) {
        if (dto == null) {
            return null;
        }
        entity.setId(dto.getId());
        entity.setText(dto.getText());
        return entity;
    }
}
