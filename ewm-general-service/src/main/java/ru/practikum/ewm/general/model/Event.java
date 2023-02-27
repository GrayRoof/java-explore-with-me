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
    @JoinColumn(name = "categoryID")
    Category category;
    @Column(name = "created")
    LocalDateTime createdOn;
    String description;
    LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "initiatorID")
    User initiator;
    @ManyToOne
    @JoinColumn(name = "locationID")
    EventLocation location;
    boolean paid;
    String title;
    @Transient
    long views;
    int participantLimit;
    boolean requestModeration;
    @Column(name = "published")
    LocalDateTime publishedOn;
    @Enumerated(EnumType.STRING)
    EventState state;

}
