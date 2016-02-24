package com.example.kayle.grade_calculator;

import java.util.ArrayList;

public class Course {
    ArrayList<Section> sections = new ArrayList<>();

    public void addSection(){
        sections.add(new Section());

    }
    public void deleteSection(Section section){
        sections.remove(section);

    }
}
