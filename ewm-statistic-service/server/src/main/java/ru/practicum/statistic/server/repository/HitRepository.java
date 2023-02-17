package ru.practicum.statistic.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.statistic.server.model.Hit;
import ru.practicum.statistic.server.model.StatisticView;

import java.time.LocalDateTime;
import java.util.List;


public interface HitRepository extends JpaRepository<Hit, Long> {

    @Query(value = "SELECT new ru.practicum.statistic.server.model.StatisticView(h.app, h.uri, COUNT (h.ip)) " +
            "FROM Hit h " +
            "WHERE h.timestamp BETWEEN ?1 AND ?2 AND h.uri IN ?3 " +
            "GROUP BY h.app, h.uri ORDER BY COUNT(h.ip) DESC")
    List<StatisticView> findNotUniqueIP(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(value = "SELECT new ru.practicum.statistic.server.model.StatisticView(h.app, h.uri, COUNT (DISTINCT h.ip)) " +
            "FROM Hit h " +
            "WHERE h.timestamp BETWEEN ?1 AND ?2 AND h.uri IN ?3 " +
            "GROUP BY h.app, h.uri ORDER BY COUNT(h.ip) DESC")
    List<StatisticView> findUniqueIp(LocalDateTime start, LocalDateTime end, List<String> uris);
}
