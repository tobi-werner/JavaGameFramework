package de.tu_darmstadt.informatik.fop.test.dorfromantik.tutor.testsuites;

import de.tu_darmstadt.informatik.fop.test.dorfromantik.tutor.testcases.AnimationTest;
import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

public class DorfromantikTestSuiteStage3 {
	public static Test suite() {
		
		TestSuite suite = new TestSuite("Tutor tests for Dorfromantik - Stage 3");
		suite.addTest(new JUnit4TestAdapter(AnimationTest.class));
		return suite;
	}
}