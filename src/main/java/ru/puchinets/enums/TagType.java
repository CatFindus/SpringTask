package ru.puchinets.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum TagType {
    SECURITY("Security", 0),
    CODE_REFACTORING("Code Refactoring", 1),
    ADD_SERVICE("Adding feature", 2),
    UPDATE("Update", 3),
    BUGS("Replasing bugs", 4);

    final String name;
    final Integer id;


    public static List<TagType> list() {
        return Arrays.stream(TagType.values()).toList();
    }

    public static TagType byId(Integer id) {
        return TagType.values()[id];
    }

    public static TagType byName(String name) {
        return Arrays.stream(TagType.values())
                .filter(taskStatus -> taskStatus.name.equals(name))
                .findFirst().orElse(null);
    }
}