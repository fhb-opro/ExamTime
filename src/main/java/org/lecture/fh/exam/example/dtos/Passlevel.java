package org.lecture.fh.exam.example.dtos;

import lombok.Getter;

@Getter
public class Passlevel {
    private final String level;
    private final int passgrade;

    /**
     * @param level     the level to be applied
     * @param passgrade minimum level for the grade
     */
    public Passlevel(String level, int passgrade) {
        this.level = level;
        this.passgrade = passgrade;
    }
}
