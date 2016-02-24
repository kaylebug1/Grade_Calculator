package com.example.kayle.grade_calculator;

import com.example.kayle.grade_calculator.Course;

import org.junit.Test;


/**
 * Created by Kayle on 2/24/2016.
 */
public class CourseTest {

    @Test
    public void isItMade(){
        Course c = new Course();
        c.addSection();
        assert(!c.sections.isEmpty());
    }

    @Test
    public void delete(){
        Section s = new Section();
        Course c = new Course();
        int beforeSize = c.sections.size();
        c.addSection();
        c.deleteSection(s);
        int afterSize = c.sections.size();
        assert(beforeSize == afterSize);
    }

}
