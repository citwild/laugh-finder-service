package edu.uw.citw.util.pylaughfinder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PyLaughFinderUtilTest {

    private PyLaughFinderUtil uut;

    @Before
    public void setUp() throws Exception {
        uut = new PyLaughFinderUtil();

        uut.setMainScript("testScript.py");
        uut.setTestDir("testDir");
    }

    @Test
    public void getCommand_ShouldProvideExpectedCommand() throws Exception {
        String[] result = uut.getTestingCommand("testBucket", "testKey");

        assertEquals("testScript.py", result[1]); // script location
        assertEquals("testBucket", result[3]); // bucket
        assertEquals("testKey", result[5]); // key
        assertEquals("testDir", result[7]); // testDir
        assertEquals("0", result[9]); // phase
    }
}