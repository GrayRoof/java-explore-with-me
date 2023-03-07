package ru.practikum.ewm.general.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Table(name = "rate_event_reactions")
public class EventReactionKey implements Serializable {

    @Column(name = "userid")
    Long userId;
    @Column(name = "eventid")
    Long eventId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventReactionKey that = (EventReactionKey) o;
        return Objects.equals(userId, that.userId) && Objects.equals(eventId, that.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, eventId);
    }
}
