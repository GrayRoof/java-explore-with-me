package ru.practikum.ewm.general.model;

import lombok.*;

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
    EventLocation location;
    boolean paid;
    String title;
    @Transient
    long views;
    @Column(name = "participantlimit")
    int participantLimit;
    @Column(name = "requestmoderation")
    boolean requestModeration;
    @Column(name = "published")
    LocalDateTime publishedOn;
    @Enumerated(EnumType.STRING)
    EventState state;

}
