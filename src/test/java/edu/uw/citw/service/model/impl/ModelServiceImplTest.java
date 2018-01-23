package edu.uw.citw.service.model.impl;

import edu.uw.citw.persistence.domain.AudioVideoMapping;
import edu.uw.citw.persistence.domain.LaughterInstance;
import edu.uw.citw.persistence.domain.ModelData;
import edu.uw.citw.persistence.repository.AudioVideoMappingRepository;
import edu.uw.citw.persistence.repository.LaughterInstanceRepository;
import edu.uw.citw.persistence.repository.ModelDataRepository;
import edu.uw.citw.util.JsonNodeAdapter;
import edu.uw.citw.util.pylaughfinder.PyLaughFinderUtil;
import edu.uw.citw.util.weka.WekaModelUtil;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ModelServiceImplTest {

    private ModelServiceImpl uut;

    private JsonNodeAdapter jna;
    private ModelDataRepository mr;
    private WekaModelUtil wmu;
    private LaughterInstanceRepository lir;
    private AudioVideoMappingRepository amr;
    private PyLaughFinderUtil plu;

    @Before
    public void setUp() throws Exception {
        jna = mock(JsonNodeAdapter.class);
        mr = mock(ModelDataRepository.class);
        wmu = mock(WekaModelUtil.class);
        lir = mock(LaughterInstanceRepository.class);
        amr = mock(AudioVideoMappingRepository.class);
        plu = mock(PyLaughFinderUtil.class);

        uut = new ModelServiceImpl(jna, mr, wmu, lir, amr, plu);
    }

    /**
     * Should return expected result.
     */
    @Test
    public void getAllModels_1() {
        when(mr.getAll())
                .thenReturn(getFakeModelList());

        when(jna.createJsonArray(eq("models"), any()))
                .thenCallRealMethod();

        String result = uut.getAllModels();

        assertThat(result, equalTo(expectedResult()));
    }

    /**
     * Should create expected format
     */
    @Test
    public void getReTrainSamplesAsJson_1() {
        when(lir.getAllMarkedForRetraining()).thenReturn(
                Arrays.asList(
                        new LaughterInstance(1L, 2L, 123456L, 123466L),
                        new LaughterInstance(2L, 3L, 123456L, 123466L)
                )
        );
        AudioVideoMapping avm1 = new AudioVideoMapping("bucket1", "vid1.mp4", "aud1.wav");
        avm1.setId(2L);
        AudioVideoMapping avm2 = new AudioVideoMapping("bucket1", "vid1.mp4", "aud1.wav");
        avm2.setId(3L);

        when(amr.findById(2)).thenReturn(Collections.singletonList(avm1));
        when(amr.findById(3)).thenReturn(Collections.singletonList(avm2));

        String result = uut.getReTrainSamplesAsJson();

        assertThat(result, equalTo(
                "{\"files\": [{\"key\" = \"aud1.wav\",\"bucket\" = \"bucket1\",\"instances\" = [{\"start\"=123.456,\"stop\"=123.466,\"correct\"=\"Y\"},]},{\"key\" = \"aud1.wav\",\"bucket\" = \"bucket1\",\"instances\" = [{\"start\"=123.456,\"stop\"=123.466,\"correct\"=\"Y\"},]},]}"
        ));
    }

    private List<ModelData> getFakeModelList() {
        Date date = new Date(Calendar.getInstance().getTimeInMillis());
        date.setTime(1514863653220L);

        ModelData model = new ModelData();
        model.setId(1L);
        model.setModelBinary(new byte[]{});
        model.setArffData("arff stuff");
        model.setCreatedDate(date);
        model.setCreatedBy("user");
        model.setInUse(true);
        return Collections.singletonList(model);
    }

    private String expectedResult() {
        return "{" +
            "\"models\":[" +
                "{" +
                    "\"id\":1," +
                    "\"createdDate\":\"2018-27-01 07:27:33\"," +
                    "\"createdBy\":\"user\"," +
                    "\"inUse\":true" +
                "}" +
            "]" +
        "}";
    }

}