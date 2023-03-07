package ru.practikum.ewm.general.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "rate_event_reactions")
public class EventReaction {
    @EmbeddedId
    EventReactionKey pk = new EventReactionKey();
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userid")
    User user;
    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "eventid")
    Event event;
    boolean positive;

    public EventReaction(User user, Event event, boolean positive) {
        this.user = user;
        this.event = event;
        this.positive = positive;
    }
}
