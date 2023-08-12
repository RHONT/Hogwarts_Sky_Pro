package ru.hogwarts.school.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.constants.ViewSelect;
import ru.hogwarts.school.exceptionHandler.IllegalFormatContentException;
import ru.hogwarts.school.exceptionHandler.NoContentException;
import ru.hogwarts.school.exceptionHandler.NotFoundStudentException;
import ru.hogwarts.school.exceptionHandler.PageErrorInputException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {
    private final Logger log = LoggerFactory.getLogger(AvatarService.class);
    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    public AvatarService(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
        log.info("@Bean AvatarService is created");
    }


    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        log.info("method uploadAvatar is run");

        Student student = studentRepository.
                findById(studentId).
                orElseThrow(() -> {
                    log.warn("student with id = " + studentId + "not found");
                    return new NotFoundStudentException("Студента с id " + studentId + " не существует");
                });
        log.debug("Found student = " + student);

        String extension = getExtension(Objects.requireNonNull(avatarFile.getOriginalFilename()));

        if (!ViewSelect.onlyImage(extension)) {
            log.warn("Format " + extension + "not supported in ViewSelect.class");
            throw new IllegalFormatContentException(extension);
        }

        Path pathFile = Path.of(avatarsDir, student.getName() + "." + extension);
        // abc/abc/test.text :: getParent - > abc/abc/
        // createDirectories - создает директории если их нет.
        Files.createDirectories(pathFile.getParent());
        Files.deleteIfExists(pathFile);

        log.debug("Path for save Avatar = " + pathFile);

        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream out = Files.newOutputStream(pathFile, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 2048);
                BufferedOutputStream bout = new BufferedOutputStream(out, 2048);
        ) {
            bis.transferTo(bout);
            log.debug("file saved successfully");
        }

        Avatar avatar = findAvatar(studentId);

        log.debug("Found avatar = " + avatar);

        if (avatar == null) {
            avatar = new Avatar();
            log.debug("new Avatar is created");
        }

        avatar.setData(avatarFile.getBytes());
        avatar.setFilePath(pathFile.toString());
        avatar.setStudent(student);
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setFileSize(avatarFile.getSize());

        log.debug("Avatar is completed :" + avatar);

        avatarRepository.save(avatar);
    }


    public Avatar findAvatar(Long student_id) {
        log.info("method findAvatar is run");
        Avatar avatar = avatarRepository.findByStudentId(student_id).orElseThrow(() -> {
            log.warn("Student with id " + student_id + " not have an avatar");
            return new NoContentException("The student does not have an avatar");
        });
        log.debug("Student with id = " + student_id + "have the avatar with id = " + avatar.getId());

        return avatar;
    }

    private String getExtension(String fileName) {
        log.info("method getExtension is run");
        String substring = fileName.substring(fileName.lastIndexOf(".") + 1);
        log.debug("File extension is " + substring);
        return substring;
    }


    public List<Avatar> getPage(Integer pageNumber, Integer size) {
        log.info("method getPage is run");
        if (pageNumber < 1 || size < 1) {
            log.warn("input incorrect number and size of page = " + pageNumber + ":" + size);
            throw new PageErrorInputException("input incorrect number and size of page.");
        }
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, size);

        List<Avatar> content = avatarRepository.findAll(pageRequest).getContent();
        log.debug("content<Avatar> = " + content);
        return content;
    }
}
