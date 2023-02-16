package ru.practicum.statistic.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.statistic.server.model.Hit;
import ru.practicum.statistic.server.model.StatisticView;

import java.time.LocalDateTime;
import java.util.List;


public interface HitRepository extends JpaRepository<Hit, Long> {

    @Query(value = "SELECT new ru.practicum.statistic.server.model.StatisticView(Hit.app, Hit.uri, COUNT (Hit.ip)) " +
            "FROM Hit " +
            "WHERE Hit.timestamp BETWEEN ?1 AND ?2 AND Hit.uri IN ?3 " +
            "GROUP BY Hit.app, Hit.uri ")
    List<StatisticView> findNotUniqueIP(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(value = "SELECT new ru.practicum.statistic.server.model.StatisticView(Hit.app, Hit.uri, COUNT (DISTINCT Hit.ip)) " +
            "FROM Hit " +
            "WHERE Hit.timestamp BETWEEN ?1 AND ?2 AND Hit.uri IN ?3 " +
            "GROUP BY Hit.app, Hit.uri")
    List<StatisticView> findUniqueIp(LocalDateTime start, LocalDateTime end, List<String> uris);
}
