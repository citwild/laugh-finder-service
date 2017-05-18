package edu.uw.citw.util.persistence;

import edu.uw.citw.persistence.domain.Participant;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

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

    private List<Participant> getStubParticipantList() {
        Participant part1 = new Participant(1L, 1L, "Dan", 4);
        Participant part2 = new Participant(2L, 1L, "Jim",1);
        Participant part3 = new Participant(3L, 1L, "Jane", 3);
        Participant part4 = new Participant(4L, 1L, "Danika", 2);

        return Arrays.asList(part1, part2, part3, part4);
    }
}