package edu.uw.citw.service.metadata.impl;

import com.fasterxml.jackson.databind.JsonNode;
import edu.uw.citw.service.metadata.MetadataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * For adding and getting metadata related to specific videos/audio.
 *
 * Created by miles on 2/11/17.
 */
@Service
public class MetadataServiceImpl implements MetadataService {

    private static final Logger log = LoggerFactory.getLogger(MetadataServiceImpl.class);

    public MetadataServiceImpl() {}

    @Override
    public JsonNode postMetadataPerInstance() {
        return null;
    }
}
