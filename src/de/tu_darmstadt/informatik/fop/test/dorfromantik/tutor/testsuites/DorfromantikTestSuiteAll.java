package de.tu_darmstadt.informatik.fop.test.dorfromantik.tutor.testsuites;

import junit.framework.Test;
import junit.framework.TestSuite;

public class DorfromantikTestSuiteAll {

	public static Test suite() {
		
		TestSuite suite = new TestSuite("All tutor tests for Dorfromantik");
		suite.addTest(DorfromatikTestSuiteMinimal.suite());
		suite.addTest(DorfromantikTestSuiteStage1.suite());
		suite.addTest(DorfromantikTestSuiteStage2.suite());
		suite.addTest(DorfromantikTestSuiteStage3.suite());
		return suite;
	}
}

