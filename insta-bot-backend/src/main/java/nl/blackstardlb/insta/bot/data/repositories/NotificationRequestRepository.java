package nl.blackstardlb.insta.bot.data.repositories;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.annotation.EntityGraph;
import io.micronaut.data.jpa.repository.JpaRepository;
import nl.blackstardlb.insta.bot.data.entities.NotificationRequest;

import javax.persistence.Entity;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRequestRepository extends JpaRepository<NotificationRequest, Long> {
    @EntityGraph(attributePaths = {"instagramInformation", "lastInstagramPost"})
    List<NotificationRequest> findByIdIsNotNull();

    @EntityGraph(attributePaths = {"instagramInformation", "lastInstagramPost"})
    List<NotificationRequest> findByIsEnabledTrue();

    @EntityGraph(attributePaths = {"instagramInformation"})
    Optional<NotificationRequest> findByDiscordServerId(String discordServerId);
}
