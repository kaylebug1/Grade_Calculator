package com.example.kayle.grade_calculator;

import org.junit.Test;

/**
 * Created by Steve on 2/24/2016.
 */
public class CalculatorTest {

    Kalkul8r calc = new Kalkul8r();

    @Test
    public void testCalcGrade() {
        Course course = new Course();
        assert(calc.calcGrade(course) == 0.0);
    }

    @Test
    public void testProjectNeededGrade() {
        Course course = new Course();
        double grade = 0.0;
        assert(calc.projectNeededGrade(course, grade) == 0.0);
    }

    @Test
    public void testCalcForGrade() {
        Course course = new Course();
        assert(calc.calcForGrade(course) == 0.0);
    }
}
