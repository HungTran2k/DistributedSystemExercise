package sample;

import java.io.Serializable;

public class Student implements Serializable {
    private String name;
    private String id;
    private String year;
    private String gender;

    public Student(String name, String id, String year, String gender){
        this.name = name;
        this.id = id;
        this.year = year;
        this.gender = gender;
    }

    public String getName(){
        return name;
    }
    public String getId() {
        return id;
    }
    public String getYear() {
        return year;
    }
    public String getGender() {
        return gender;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
}
