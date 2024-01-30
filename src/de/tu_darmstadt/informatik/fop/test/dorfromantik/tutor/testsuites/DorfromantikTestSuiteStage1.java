package de.tu_darmstadt.informatik.fop.test.dorfromantik.tutor.testsuites;

import de.tu_darmstadt.informatik.fop.test.dorfromantik.tutor.testcases.ConfigParserTest;
import de.tu_darmstadt.informatik.fop.test.dorfromantik.tutor.testcases.PlaceholderTileTest;
import de.tu_darmstadt.informatik.fop.test.dorfromantik.tutor.testcases.PreviewTileTest;
import de.tu_darmstadt.informatik.fop.test.dorfromantik.tutor.testcases.TileDataTest;
import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

public class DorfromantikTestSuiteStage1 {
	public static Test suite() {
		
		TestSuite suite = new TestSuite("Tutor tests for Dorfromantik - Stage 1");
		suite.addTest(new JUnit4TestAdapter(PlaceholderTileTest.class));
		suite.addTest(new JUnit4TestAdapter(TileDataTest.class));
		suite.addTest(new JUnit4TestAdapter(PreviewTileTest.class));
		suite.addTest(new JUnit4TestAdapter(ConfigParserTest.class));
		return suite;
	}
}
