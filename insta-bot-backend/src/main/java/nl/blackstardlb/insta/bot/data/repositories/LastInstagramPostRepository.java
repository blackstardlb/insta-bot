package nl.blackstardlb.insta.bot.data.repositories;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import nl.blackstardlb.insta.bot.data.entities.LastInstagramPost;

@Repository
public interface LastInstagramPostRepository extends JpaRepository<LastInstagramPost, String> {
}
