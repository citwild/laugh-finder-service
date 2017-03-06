package edu.uw.citw.util.persistence;

import edu.uw.citw.persistence.domain.InstanceParticipant;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ParticipantModelConverterTest {

    private ParticipantModelConverter unitUnderTest;

    @Before
    public void setUp() throws Exception {
        this.unitUnderTest = new ParticipantModelConverter();
    }

    @Test
    public void getModelFromDbValues_shouldProvideTheExpectedValues() throws Exception {
        unitUnderTest.getModelFromDbValues(getStubParticipantList());
    }

    private List<InstanceParticipant> getStubParticipantList() {
        InstanceParticipant part1 = new InstanceParticipant(1L, 1L, "Dan", 4);
        InstanceParticipant part2 = new InstanceParticipant(2L, 1L, "Jim",1);
        InstanceParticipant part3 = new InstanceParticipant(3L, 1L, "Jane", 3);
        InstanceParticipant part4 = new InstanceParticipant(4L, 1L, "Danika", 2);

        return Arrays.asList(part1, part2, part3, part4);
    }
}