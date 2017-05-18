package edu.uw.citw.util.persistence;

import edu.uw.citw.model.LaughParticipant;
import edu.uw.citw.persistence.domain.Participant;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * For converting Participant database information into expected JSON model.
 *
 * Created by miles on 2/11/17.
 */
public class ParticipantModelConverter {

    @Nonnull
    public LaughParticipant getModelFromDbValues(@Nonnull List<Participant> dbVal) {
        return null;
    }
}
