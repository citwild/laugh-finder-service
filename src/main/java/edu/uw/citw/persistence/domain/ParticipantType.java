package edu.uw.citw.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Represents a row from the LAUGH_TYPES_PER_PARTICIPANT table.
 *
 * Created by miles on 2/12/17.
 */
@Entity
@Table(name = "laugh_types_per_participant", schema = "uwbwfe")
public class ParticipantType implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "TYPE_ID", nullable = false)
    private Long typeId;

    @Column(name = "PARTICIPANT_ID", nullable = false)
    private Long participantId;
}
