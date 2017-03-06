package edu.uw.citw.util.persistence;

import edu.uw.citw.model.FoundLaughter;
import edu.uw.citw.model.LaughInstance;
import edu.uw.citw.persistence.domain.AudioVideoMapping;
import edu.uw.citw.persistence.domain.LaughterInstance;
import edu.uw.citw.persistence.repository.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InstancePersistenceUtilTest {

    private AudioVideoMappingRepository audioVideoMapping;
    private LaughterInstanceRepository laughterInstanceRepository;
    private InstanceParticipantsRepository instanceParticipantsRepository;
    private TypesPerParticipantRepository typesPerParticipantRepository;
    private LaughTypesRepository laughTypesRepository;

    private InstancePersistenceUtil unitUnderTest;

    @Before
    public void setUp() throws Exception {
        audioVideoMapping = mock(AudioVideoMappingRepository.class);
        laughterInstanceRepository = mock(LaughterInstanceRepository.class);
        instanceParticipantsRepository = mock(InstanceParticipantsRepository.class);
        typesPerParticipantRepository = mock(TypesPerParticipantRepository.class);
        laughTypesRepository = mock(LaughTypesRepository.class);

        unitUnderTest = new InstancePersistenceUtil(
                audioVideoMapping,
                laughterInstanceRepository,
                instanceParticipantsRepository,
                typesPerParticipantRepository,
                laughTypesRepository
        );
    }

    @Test
    public void getInstancesByBucketAndKey_ShouldReturnAnEmptyOptional_IfNotExactlyOneAudioVideoMappingIsFound() throws Exception {
        // return more than one value
        when(audioVideoMapping.findByBucketAndVideo(anyString(), anyString()))
                .thenReturn(Arrays.asList(new AudioVideoMapping(), new AudioVideoMapping()));

        Optional<FoundLaughter> result = unitUnderTest.getInstancesByBucketAndKey("test", "test");

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void saveInstances_ShouldSaveAnInstanceForEachStartStopInFoundLaughter() throws Exception {
        FoundLaughter stubFoundLaughter = new FoundLaughter("testFile");
        stubFoundLaughter.addInstance(1000, 2000);
        stubFoundLaughter.addInstance(3000, 4000);

        unitUnderTest.saveInstances(stubFoundLaughter, 1234);

        // verify database save method invoked
        verify(laughterInstanceRepository, atLeast(2)).save((LaughterInstance) any());
    }

    @Test
    public void createInstance_ShouldUseTheExpectedValues() throws Exception {
        LaughInstance stubLaughInstance = new LaughInstance(1000, 2000);
        long stubKey = 1234;

        LaughterInstance result = unitUnderTest.createInstance(stubLaughInstance, stubKey);

        assertTrue(stubLaughInstance.getStart() == result.getStartTime());
        assertTrue(stubLaughInstance.getStop() == result.getStopTime());
        assertTrue(stubKey == result.getS3Key());
    }

    //    @Test
//    public void saveInstances_ShouldSaveInstancesToTheDatabase() throws Exception {
//        FoundLaughter stubFoundLaughter = new FoundLaughter("testFile");
//        stubFoundLaughter.addInstance(1000, 2000);
//        stubFoundLaughter.addInstance(3000, 4000);
//
//        unitUnderTest.saveInstances(stubFoundLaughter);
//    }
}