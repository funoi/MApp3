package com.funoi.model;

import java.util.List;

public class Student {
    private Integer id, age;
    private String name, sex, edu;
    private List<String> hobby;

    public Student() {
    }

    public Student(Integer id, String name, Integer age, String sex, String edu, List<String> hobby) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.sex = sex;
        this.edu = edu;
        this.hobby = hobby;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", edu='" + edu + '\'' +
                ", hobby=" + hobby +
                '}';
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<String> getHobby() {
        return hobby;
    }

    public void setHobby(List<String> hobby) {
        this.hobby = hobby;
    }

    public String getEdu() {
        return edu;
    }

    public void setEdu(String edu) {
        this.edu = edu;
    }
}
