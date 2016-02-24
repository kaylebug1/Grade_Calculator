package com.example.kayle.grade_calculator;

import org.junit.Test;

import dalvik.annotation.TestTargetClass;

/**
 * Created by Testare on 24-Feb-16.
 */
public class SettingsTest {
    Settings s = new Settings();
    @Test
    public void testGradeSetters() {

        s = new Settings(1.0f);
        assert (s.getBaseGrade() == 1.0f);
        assert (s.getTargetGrade() == Settings.UNSET);
        s = new Settings(2.0f, false);
        assert (s.getBaseGrade() == Settings.UNSET);
        assert (s.getTargetGrade() == 2.0f);
        s = new Settings(3.0f, true);
        assert (s.getBaseGrade() == 3.0f);
        assert (s.getTargetGrade() == Settings.UNSET);
        s = new Settings();
        assert (s.getBaseGrade() == Settings.UNSET);
        assert (s.getTargetGrade() == Settings.UNSET);
        s.setBaseGrade(5.0f);
        assert (s.getBaseGrade() == 5.0f);
        assert (s.getTargetGrade() == Settings.UNSET);
        s.setTargetGrade(6.0f);
        assert (s.getTargetGrade() == 6.0f);
        assert (s.getBaseGrade() == Settings.UNSET);
        s.setBaseGrade(7.0f);
        assert (s.getBaseGrade() == 7.0f);
        assert (s.getTargetGrade() == Settings.UNSET);
        s.reset();
        assert (s.getBaseGrade() == Settings.UNSET);
        assert (s.getTargetGrade() == Settings.UNSET);

    }
}
