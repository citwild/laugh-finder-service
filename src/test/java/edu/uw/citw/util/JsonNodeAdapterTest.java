package edu.uw.citw.util;

import com.fasterxml.jackson.databind.JsonNode;
import edu.uw.citw.persistence.domain.LaughterType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class JsonNodeAdapterTest {

    private JsonNodeAdapter unitUnderTest;

    @Before
    public void setUp() throws Exception {
        this.unitUnderTest = new JsonNodeAdapter();
    }

    @Test
    public void createJsonArray_shouldCreateTheExpectedValue() throws Exception {
        JsonNode result = unitUnderTest
                .createJsonArray("test", Arrays.asList(new StubObject(), new StubObject()));

        String expected = "{\"test\":[{\"str\":\"test\",\"num\":1},{\"str\":\"test\",\"num\":1}]}";
        assertEquals(expected, result.toString());
    }

    private class StubObject {
        public String str = "test";
        public int num = 1;
    }
}