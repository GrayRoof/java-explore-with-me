package ru.practicum.statistic.server.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "hits")
public class Hit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "app", nullable = false, length = 100)
    private String app;

    @Column(name = "uri", nullable = false, length = 150)
    private String uri;

    @Column(name = "ip", nullable = false, length = 30)
    private String ip;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}
