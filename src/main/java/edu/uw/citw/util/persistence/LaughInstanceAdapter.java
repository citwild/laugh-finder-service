package edu.uw.citw.util.persistence;

import edu.uw.citw.persistence.repository.AudioVideoMappingRepository;
import edu.uw.citw.persistence.repository.LaughterInstanceRepository;
import org.springframework.stereotype.Component;

/**
 * Used to retrieve and format data from databases
 *
 * Created by Miles on 12/17/2016.
 */
@Component
public class LaughInstanceAdapter {

    private AudioVideoMappingRepository audioVideoMapping;
    private LaughterInstanceRepository laughterInstance;

    public LaughInstanceAdapter(
            AudioVideoMappingRepository audioVideoMapping,
            LaughterInstanceRepository laughterInstance) {

        this.audioVideoMapping = audioVideoMapping;
        this.laughterInstance = laughterInstance;
    }

    
}
