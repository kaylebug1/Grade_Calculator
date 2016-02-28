package com.example.kayle.grade_calculator;

import org.junit.Test;

/**
 * Created by Kayle on 2/24/2016.
 */
public class CourseTest {
    @Test
    public void isItMade(){
        Course c = new Course();
        Section s = c.addSection();
        assert((c.getSection(0) != null)
                && (c.getSection(0) == s));
    }

    @Test
    public void delete(){
        Course c = new Course();
//        int beforeSize = c.sections.size();
        Section s = c.addSection();
        //WHY ARE YOU ACCESSING THE ARRAYLIST LIKE THAT
        //NONONONONONONO
        //http://www.youtube.com/watch?v=oKI-tD0L18A
        //~"Steve"
        c.deleteSection(s);
        s = c.addSection();
        c.deleteSection(c.getSection(0));
        try{
            if(c.getSection(0) != null)
                assert false; //We haven't decided if getSection(bad index) will throw an error or
            // return null
            //But if it doesn't do either of those two things, assert false, deletion is probably
            //not working right.
        }catch (IndexOutOfBoundsException e)
        {
            //This is good, it means it was deleted successfully
        }
//        int afterSize = c.sections.size();
//        assert(beforeSize == afterSize);
    }
}
