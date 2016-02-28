package com.example.kayle.grade_calculator;

import org.junit.Test;

import java.util.List;

/**
 * Created by Testare on 27-Feb-16.
 */
public class SectionTest {

    @Test
    public void testWeighting() {
        Section s = new Section();
        assert (s.getWeight() == Section.DEFAULT_WEIGHT);
        s.setWeight(4.0f);
        assert (s.getWeight() == 4.0f);
    }

    @Test
    public void testAssignments() {
        Section s = new Section();
        Assignment a = new Assignment(1.0f),
                b = new Assignment(2.0f);
        s.addAssignment(a);
        List<Assignment> la = s.getAssignments();
        assert la.size() == 1; //Tests successful adding of assignment, and getting
        assert la.contains(a);

        s.addAssignment(b); //This should not affect the getAssignments list retroactively
        assert la.size() == 1;
        assert la.contains(a);
        assert !la.contains(b);

        List<Assignment> la2 = s.getAssignments(); //Now we get the new list of assignments
        assert la2.contains(b); //It should have b too now

        la.remove(a);   //LA: {} Section: {a, b}
        la2 = s.getAssignments(); //Should not remove elements retroactively
        assert la2.contains(a);

        s.deleteAssignment(a);
        la = s.getAssignments();
        assert la2.contains(a); //Not removed from return array list, it WAS returning a copy of the list
        assert !la.contains(a); //It WAS removed successfully
    }
}
