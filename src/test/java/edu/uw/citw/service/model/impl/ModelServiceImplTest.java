package edu.uw.citw.service.model.impl;

import edu.uw.citw.persistence.domain.ModelData;
import edu.uw.citw.persistence.repository.ModelDataRepository;
import edu.uw.citw.util.JsonNodeAdapter;
import edu.uw.citw.util.weka.WekaModelUtil;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
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

    @Before
    public void setUp() throws Exception {
        jna = mock(JsonNodeAdapter.class);
        mr = mock(ModelDataRepository.class);

        uut = new ModelServiceImpl(jna, mr, wmu);
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