package de.tu_darmstadt.informatik.fop.test.dorfromantik.student.testsuites;

import de.tu_darmstadt.informatik.fop.test.dorfromantik.student.testcases.BoardTestExtended;
import de.tu_darmstadt.informatik.fop.test.dorfromantik.student.testcases.ScoreManagerTest;
import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

public class DorfromantikTestSuiteStage2 {
	public static Test suite() {
		
		TestSuite suite = new TestSuite("Student tests for Dorfromantik - Stage 2");
		suite.addTest(new JUnit4TestAdapter(BoardTestExtended.class));
		suite.addTest(new JUnit4TestAdapter(ScoreManagerTest.class));
		return suite;
	}
}
