package com.example.kayle.grade_calculator;

import org.junit.Test;

/**
 * Created by Steve on 2/24/2016.
 */
public class CalculatorTest{

    Calculator calc = new Calculator();

    @Test
    public void testCalcGrade() {
        Course course = new Course();
        course.addSection();
        course.getSection(0).addAssignment(new Assignment());
        assert(calc.calcGrade(course) >= 0.0);
    }

    @Test
    public void testProjectNeededGrade() {
        Course course = new Course();
        course.addSection();
        course.getSection(0).addAssignment(new Assignment());
        float tempGrade = 0.0f;
        assert(calc.projectNeededGrade(course, tempGrade) >= 0.0);
    }

    /*@Test
    public void testCalcForGrade() {
        Course course = new Course();
        assert(calc.calcForGrade(course) >= 0.0);
    }*/
}