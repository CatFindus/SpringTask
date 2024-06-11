package ru.puchinets.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum TaskStatus {
    NOT_BEGINNED("Not beginned", 0),
    IN_PROGRESS("In progress", 1),
    ON_REVIEW("On review", 2),
    ON_MODIFICATION("On modification", 3),
    FINISHED("Finished", 4),
    ARCHIVED("Archived", 5);

    public final String name;
    public final Integer id;

    TaskStatus(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public static List<TaskStatus> list() {
        return Arrays.stream(TaskStatus.values()).toList();
    }

    public static TaskStatus byId(Integer id) {
        return TaskStatus.values()[id];
    }

    public static TaskStatus byName(String name) {
        return Arrays.stream(TaskStatus.values())
                .filter(taskStatus -> taskStatus.name.equals(name))
                .findFirst().orElse(null);
    }
}
