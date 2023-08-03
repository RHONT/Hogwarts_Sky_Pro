package ru.hogwarts.school.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.constants.ViewSelect;
import ru.hogwarts.school.exceptionHandler.IllegalFormatContentException;
import ru.hogwarts.school.exceptionHandler.NotFoundStudentException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {
    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    public AvatarService(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }


    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        Student student = studentRepository.
                findById(studentId).
                orElseThrow(() -> new NotFoundStudentException("Студента с id " + studentId + " не существует"));
        String extension = getExtension(Objects.requireNonNull(avatarFile.getOriginalFilename()));
        if (!ViewSelect.onlyImage(extension)) {
            throw new IllegalFormatContentException(extension);
        }
        Path pathFile = Path.of(avatarsDir, student.getName() + "." + extension);
        // abc/abc/test.text :: getParent - > abc/abc/
        // createDirectories - создает директории если их нет.
        Files.createDirectories(pathFile.getParent());
        Files.deleteIfExists(pathFile);

        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream out = Files.newOutputStream(pathFile, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 2048);
                BufferedOutputStream bout = new BufferedOutputStream(out, 2048);
        ) {
            bis.transferTo(bout);
        }

        Avatar avatar = findAvatar(studentId);
        if (avatar == null) {
            avatar = new Avatar();
        }
        avatar.setData(avatarFile.getBytes());
        avatar.setFilePath(pathFile.toString());
        avatar.setStudent(student);
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setFileSize(avatarFile.getSize());
        avatarRepository.save(avatar);
    }


    public Avatar findAvatar(Long student_id) {
        return avatarRepository.findByStudentId(student_id).orElse(null);
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }


}
