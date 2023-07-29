package ru.hogwarts.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.ExceptionHandler.NotFoundFaculty;
import ru.hogwarts.school.model.Faculty;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FacultyServices {
    private final Map<Long, Faculty> storageFaculty = new HashMap<>();
    private static long count = 1;

    public Faculty add(Faculty faculty) {
        faculty.setId(count);
        storageFaculty.put(count++, faculty);
        return faculty;
    }

    @PostConstruct
    private void init() {
        add(new Faculty(0, "Слизерин", "green"));
        add(new Faculty(0, "Гриффиндор", "red"));
        add(new Faculty(0, "Пуффендуй", "red"));
        add(new Faculty(0, "Руппендуй", "red"));
        add(new Faculty(0, "Гаррандор", "yellow"));
    }

    public Faculty remove(long id) {

        if (storageFaculty.containsKey(id)) {
            return storageFaculty.remove(id);
        }
        throw new NotFoundFaculty("Нет такого faculty");
    }

    public Faculty get(long id) {
        if (storageFaculty.containsKey(id)) {
            return storageFaculty.get(id);
        }
        throw new NotFoundFaculty("Нет такого faculty");
    }

    public List<Faculty> getAllStudent() {
        return new ArrayList<>(storageFaculty.values());
    }

    public Faculty update(Faculty faculty) {

        if (storageFaculty.containsKey(faculty.getId())) {
            storageFaculty.put(faculty.getId(), faculty);
            return faculty;
        }
        throw new NotFoundFaculty("Нет такого faculty");
    }

}
