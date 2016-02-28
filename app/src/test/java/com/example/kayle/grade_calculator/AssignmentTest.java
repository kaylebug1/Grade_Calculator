package com.example.kayle.grade_calculator;

import org.junit.Test;

/**
 * Created by Testare on 27-Feb-16.
 */
public class AssignmentTest {

    @Test
    public void testValues() {

        Assignment a = new Assignment();
        assert(a.getPointValue() == 0.0f);
        assert(a.getPointsEarned() == 0.0f);
        a = new Assignment(1.0f);
        assert(a.getPointValue() == 1.0f);
        assert(a.getPointsEarned() == 0.0f);
        a = new Assignment(3.0f,2.0f);
        assert(a.getPointValue() == 3.0f);
        assert(a.getPointsEarned() == 2.0f);
        a.setPointValue(4.0f);
        a.setPointsEarned(2.0f);
        assert(a.getPointsEarned() == 2.0f);
        assert(a.getPointValue() == 4.0f);
        a.setPointsEarned(6.0f);
        a.setPointValue(8.0f);
        assert(a.getPointsEarned() == 6.0f);
        assert(a.getPointValue() == 8.0f);
    }
}
