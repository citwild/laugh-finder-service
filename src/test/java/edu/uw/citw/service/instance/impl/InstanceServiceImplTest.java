package edu.uw.citw.service.instance.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uw.citw.persistence.domain.AudioVideoMapping;
import edu.uw.citw.persistence.domain.LaughterInstance;
import edu.uw.citw.persistence.domain.Tag;
import edu.uw.citw.persistence.repository.AudioVideoMappingRepository;
import edu.uw.citw.persistence.repository.LaughterInstanceRepository;
import edu.uw.citw.persistence.repository.TagsRepository;
import edu.uw.citw.util.JsonNodeAdapter;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class InstanceServiceImplTest {

    private InstanceServiceImpl uut;

    private JsonNodeAdapter jna;
    private LaughterInstanceRepository lir;
    private TagsRepository tr;
    private AudioVideoMappingRepository avmr;

    @Before
    public void setUp() throws Exception {
        jna = mock(JsonNodeAdapter.class);
        lir = mock(LaughterInstanceRepository.class);
        tr = mock(TagsRepository.class);
        avmr = mock(AudioVideoMappingRepository.class);

        uut = new InstanceServiceImpl(jna, lir, tr, avmr);
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
        returnedInst.setUseForRetrain(true);
        return Collections.singletonList(returnedInst);
    }

    private List<AudioVideoMapping> getFakeVideoMapping() {
        AudioVideoMapping video = new AudioVideoMapping();
        video.setId(1L);
        video.setBucket("lfassets");
        video.setVideoFile("video/test-video.mp4");
        return Collections.singletonList(video);
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

    private String expectedRetrainSampleResult() {
        return "{\"retrainingSamples\":[" +
                    "{" +
                        "\"bucket\":\"lfassets\"," +
                        "\"key\":\"video/test-video.mp4\"," +
                        "\"startTime\":1.0," +
                        "\"stopTime\":1.8," +
                        "\"algCorrect\":true" +
                    "}" +
                "]}";
    }
}