package ru.hogwarts.school.constants;


import java.util.Set;

public final class ViewSelect {
    private final static Set<String> listImage = Set.of("jpg", "png", "bmp");

    public static boolean onlyImage(String extension) {
        return listImage.contains(extension);
    }
}
