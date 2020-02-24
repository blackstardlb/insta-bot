package nl.blackstardlb.insta.bot.service;

import nl.blackstardlb.insta.bot.data.entities.LastInstagramPost;
import nl.blackstardlb.insta.bot.data.repositories.LastInstagramPostRepository;

import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class LastInstagramPostService {
    private final LastInstagramPostRepository lastInstagramPostRepository;

    public LastInstagramPostService(LastInstagramPostRepository lastInstagramPostRepository) {
        this.lastInstagramPostRepository = lastInstagramPostRepository;
    }

    public LastInstagramPost getOrSave(LastInstagramPost lastInstagramPost) {
        return get(lastInstagramPost.getId()).orElseGet(() -> lastInstagramPostRepository.save(lastInstagramPost));
    }

    public Optional<LastInstagramPost> get(String id) {
        return lastInstagramPostRepository.findById(id);
    }
}
