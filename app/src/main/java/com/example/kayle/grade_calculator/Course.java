package com.example.kayle.grade_calculator;

import java.util.ArrayList;

public class Course {
    //Should have private access
    private ArrayList<Section> sections = new ArrayList<>();
    private String courseName;

    Course(String name) {
        courseName = name;
    }

    public Section addSection(){
        Section s = new Section();
        sections.add(s);
        return s;

    }

    public void deleteSection(Section section){
        sections.remove(section);
    }

    public Section getSection(int i) {
        return new Section();
    }
}
