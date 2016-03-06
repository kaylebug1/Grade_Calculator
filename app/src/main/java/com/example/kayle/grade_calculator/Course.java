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
        Section s = new Section(sections.size() + 1);
        sections.add(s);
        return s;
    }
    public Section addSection(String name) {
        Section s = new Section(name);
        sections.add(s);
        return s;
    }

    public String getCourseName() {
        return courseName;
    }

    public void deleteSection(Section section){
        sections.remove(section);
    }

    public Section getSection(int i) {
        return sections.get(i);
    }
    public int getSectionCount() {
        return sections.size();
    }
}
