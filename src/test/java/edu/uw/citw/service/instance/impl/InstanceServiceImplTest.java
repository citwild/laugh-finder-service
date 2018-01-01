package edu.uw.citw.service.instance.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uw.citw.persistence.domain.LaughterInstance;
import edu.uw.citw.persistence.domain.Tag;
import edu.uw.citw.persistence.repository.LaughterInstanceRepository;
import edu.uw.citw.persistence.repository.TagsRepository;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class InstanceServiceImplTest {

    private InstanceServiceImpl uut;

    private LaughterInstanceRepository lir;
    private TagsRepository tr;

    @Before
    public void setUp() throws Exception {
        lir = mock(LaughterInstanceRepository.class);
        tr = mock(TagsRepository.class);

        uut = new InstanceServiceImpl(lir, tr);
    }

    /**
     * updateInstance should submit the expected values to the instance and tag databases.
     */
    @Test
    public void updateInstance_1() throws Exception {
        // Create the expected instance value to submit
        LaughterInstance expected = getFakeInstanceValue().get(0);
        expected.setAlgCorrect(false);

        // When queried, give database return value
        when(lir.findById(1L)).thenReturn(getFakeInstanceValue());
        when(tr.findAllPerInstance(1L)).thenReturn(Collections.emptyList());

        uut.updateInstance(1L, getFakeJsonPayload());

        // Confirm any original tags are deleted before writing a new list
        //   0 because no values returned
        verify(tr, times(0)).deleteTagsByInstanceIdEquals(1L);

        // Confirm databases get what we want them to
        verify(lir, times(1)).save(expected);
        verify(tr, times(1)).save(getSubmittedTags());
    }

    /**
     * Simulate the Instance result from the database service
     */
    private List<LaughterInstance> getFakeInstanceValue() {
        LaughterInstance returnedInst = new LaughterInstance();
        returnedInst.setId(1L);
        returnedInst.setStartTime(1000L);
        returnedInst.setStopTime(1800L);
        returnedInst.setAlgCorrect(true);
        returnedInst.setS3Key(1L);
        returnedInst.setUserMade(false);
        return Arrays.asList(returnedInst);
    }

    private List<Tag> getSubmittedTags() {
        Tag one = new Tag(1L, 1L);
        Tag two = new Tag(1L, 2L);
        Tag three = new Tag(1L, 3L);

        return Arrays.asList(one, two, three);
    }

    private JsonNode getFakeJsonPayload() throws IOException {
        String payload = "{\"algCorrect\":false, \"tags\": [ 1, 2, 3 ] }";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode val = mapper.readTree(payload);
        return val;
    }
}