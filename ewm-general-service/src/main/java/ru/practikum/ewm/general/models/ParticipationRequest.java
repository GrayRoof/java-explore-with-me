package ru.practikum.ewm.general.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practikum.ewm.general.models.enums.RequestStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "requests")
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDateTime created;
    @ManyToOne
    @JoinColumn(name = "eventID")
    Event event;
    @ManyToOne
    @JoinColumn(name = "requesterID")
    User requester;
    @Enumerated(EnumType.STRING)
    RequestStatus status;
}
