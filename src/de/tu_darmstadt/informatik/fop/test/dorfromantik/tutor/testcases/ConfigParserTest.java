package de.tu_darmstadt.informatik.fop.test.dorfromantik.tutor.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.tu_darmstadt.informatik.fop.main.dorfromantik.parser.ConfigParser;

public class ConfigParserTest {

	@Test
	public final void testConfingParsing() {
		ConfigParser parser = new ConfigParser();
    	parser.parseConfigFromPath("assets/config.properties");
    	
    	assertNotNull(parser.getGameData());
    	assertTrue(parser.validateData());
	}
	
}
