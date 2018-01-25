package edu.uw.citw.model;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class RetrainSampleFileTest {

    /**
     * Should be in the proper format
     */
    @Test
    public void toString_1() {

        RetrainSampleInstance i1 = new RetrainSampleInstance(123456L, 123466L, true);
        RetrainSampleInstance i2 = new RetrainSampleInstance(123456L, 123466L, true);

        RetrainSampleFile uut = new RetrainSampleFile(
                "lfassets",
                "video/someone.mp4",
                Arrays.asList(i1,i2)
        );

        assertThat(uut.toString(), equalTo(
                "{\"key\": \"video/someone.mp4\", \"bucket\": \"lfassets\", \"instances\": [{\"start\": 123.456, \"stop\": 123.466, \"correct\": \"Y\"},{\"start\": 123.456, \"stop\": 123.466, \"correct\": \"Y\"}]}"
        ));
    }
}