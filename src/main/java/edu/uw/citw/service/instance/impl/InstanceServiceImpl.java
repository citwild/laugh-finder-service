package edu.uw.citw.service.instance.impl;

import edu.uw.citw.persistence.repository.LaughterInstanceRepository;
import edu.uw.citw.service.instance.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

/**
 * For getting and modifying instances.
 *
 * Created by miles on 3/5/17.
 */
@Service
public class InstanceServiceImpl implements InstanceService {

    private LaughterInstanceRepository instanceRepository;

    @Autowired
    public InstanceServiceImpl(LaughterInstanceRepository instanceRepository) {
        this.instanceRepository = instanceRepository;
    }

    @Override
    public String deleteInstance(@Nonnull Long id) {
        instanceRepository.delete(id);
        return "{\"message\":\"success\"}";
    }
}
