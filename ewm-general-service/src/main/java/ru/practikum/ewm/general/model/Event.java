package ru.practikum.ewm.general.model;

import lombok.*;
import org.hibernate.annotations.Cascade;
import ru.practikum.ewm.general.model.enums.EventState;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String annotation;
    @ManyToOne
    @JoinColumn(name = "categoryid")
    Category category;
    @Column(name = "created")
    LocalDateTime createdOn;
    String description;
    @Column(name = "eventdate")
    LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "initiatorid")
    User initiator;
    @ManyToOne
    @JoinColumn(name = "locationid")
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    EventLocation location;
    boolean paid;
    String title;
    @Column(name = "participantlimit")
    long participantLimit;
    @Column(name = "requestmoderation")
    boolean requestModeration;
    @Column(name = "published")
    LocalDateTime publishedOn;
    @Enumerated(EnumType.STRING)
    EventState state;
    @Column(name = "viewscache")
    long views;
    @Transient
    long confirmedRequests;
}
