package edu.uw.citw.service.metadata.impl;

import edu.uw.citw.persistence.repository.InstanceParticipantsRepository;
import edu.uw.citw.persistence.repository.LaughterInstanceRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import static org.hamcrest.Matchers.equalTo;


public class MetadataServiceImplTest {

    private LaughterInstanceRepository lir;
    private InstanceParticipantsRepository ipr;

    private MetadataServiceImpl uut;

    @Before
    public void setUp() throws Exception {
        lir = mock(LaughterInstanceRepository.class);
        ipr = mock(InstanceParticipantsRepository.class);

        uut = new MetadataServiceImpl(lir, ipr);
    }

    @Test
    public void convertSecondsToMs_shouldTurnFloatToLong() throws Exception {
        Long result = uut.convertSecondsToMs(22.277);
        assertThat(result, equalTo(22277L));
    }
}