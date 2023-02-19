package ru.practicum.statistic.server.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "hits")
public class Hit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "app", nullable = false, length = 100)
    String app;

    @Column(name = "uri", nullable = false, length = 150)
    String uri;

    @Column(name = "ip", nullable = false, length = 30)
    String ip;

    @Column(name = "timestamp")
    LocalDateTime timestamp;

    @Override
    public int hashCode() {
        final int rate = 31;
        int result = 1;
        result = (int) (rate * result + getId());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Hit)) return false;
        Hit other = (Hit) o;
        return this.getId() == null ? other.getId() == null : this.getId().equals(other.getId());
    }
}
