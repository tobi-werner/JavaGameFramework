package de.tu_darmstadt.informatik.fop.test.dorfromantik.tutor.testsuites;

import de.tu_darmstadt.informatik.fop.test.dorfromantik.tutor.testcases.BoardControllerTest;
import de.tu_darmstadt.informatik.fop.test.dorfromantik.tutor.testcases.BoardTest;
import de.tu_darmstadt.informatik.fop.test.dorfromantik.tutor.testcases.MenuStateTest;
import de.tu_darmstadt.informatik.fop.test.dorfromantik.tutor.testcases.TextureControllerTest;
import de.tu_darmstadt.informatik.fop.test.dorfromantik.tutor.testcases.TileFactoryTest;
import de.tu_darmstadt.informatik.fop.test.dorfromantik.tutor.testcases.TileMapTest;
import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

public class DorfromatikTestSuiteMinimal {
		
		public static Test suite() {
			
			TestSuite suite = new TestSuite("Tutor tests for Dorfromantik - Minimal");
			suite.addTest(new JUnit4TestAdapter(TileMapTest.class));
			suite.addTest(new JUnit4TestAdapter(BoardTest.class));
			suite.addTest(new JUnit4TestAdapter(TextureControllerTest.class));
			suite.addTest(new JUnit4TestAdapter(TileFactoryTest.class));
			suite.addTest(new JUnit4TestAdapter(MenuStateTest.class));
			suite.addTest(new JUnit4TestAdapter(BoardControllerTest.class));
			return suite;
		}
	}
