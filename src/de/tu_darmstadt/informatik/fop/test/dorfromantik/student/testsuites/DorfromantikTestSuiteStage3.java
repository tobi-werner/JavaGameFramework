package de.tu_darmstadt.informatik.fop.test.dorfromantik.student.testsuites;

import de.tu_darmstadt.informatik.fop.test.dorfromantik.student.testcases.AnimationTest;
import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

public class DorfromantikTestSuiteStage3 {
	public static Test suite() {
		
		TestSuite suite = new TestSuite("Student tests for Dorfromantik - Stage 3");
		suite.addTest(new JUnit4TestAdapter(AnimationTest.class));
		return suite;
	}
}