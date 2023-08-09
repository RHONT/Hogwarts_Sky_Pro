package ru.hogwarts.school.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exceptionHandler.NotFoundStudentException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.services.AvatarService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @GetMapping("/page-avatar")
    public List<Avatar> getPageAvatar(@RequestParam(name = "page") Integer pageNumber,
                                      @RequestParam(name = "size") Integer size) {

        return avatarService.getPage(pageNumber, size);
    }

    // Post принимает извне некий медиафайл.
    @PostMapping(value = "/{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long studentId, @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() > 1024 * 1024) {
            return ResponseEntity.badRequest().body("File is too big");
        }

        avatarService.uploadAvatar(studentId, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{studentId}/get-from-db-avatar")
    public ResponseEntity<byte[]> getAvatarFromDb(@PathVariable Long studentId) {
        Avatar avatar = avatarService.findAvatar(studentId);
        if (avatar == null) {
            throw new NotFoundStudentException("Студента с id " + studentId + " не существет");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping("/{studentId}/get-from-local-avatar")
    public void getAvatarFromLocal(@PathVariable Long studentId, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatar(studentId);
        if (avatar == null) {
            throw new NotFoundStudentException("Студента с id " + studentId + " не существет");
        }
        Path path = Path.of(avatar.getFilePath());
        try (
                InputStream is = Files.newInputStream(path);
                OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
//            response.setContentLength((int) avatar.getFileSize());
            response.setHeader("Content-Length", String.valueOf(avatar.getFileSize()));
            is.transferTo(os);
        }
    }
}
