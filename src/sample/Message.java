package sample;

import java.io.Serializable;

public final class Message implements Serializable {
    private Student student;

    Message(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }
}
