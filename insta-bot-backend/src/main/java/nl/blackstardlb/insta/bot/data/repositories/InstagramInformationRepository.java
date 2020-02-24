package nl.blackstardlb.insta.bot.data.repositories;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import nl.blackstardlb.insta.bot.data.entities.InstagramInformation;

@Repository
public interface InstagramInformationRepository extends JpaRepository<InstagramInformation, String> {
}
