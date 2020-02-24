package nl.blackstardlb.insta.bot.service;

import lombok.extern.slf4j.Slf4j;
import nl.blackstardlb.insta.bot.data.entities.InstagramInformation;
import nl.blackstardlb.insta.bot.data.instagram.InstagramClient;
import nl.blackstardlb.insta.bot.data.instagram.models.request.QueryParams;
import nl.blackstardlb.insta.bot.data.repositories.InstagramInformationRepository;

import javax.inject.Singleton;
import java.util.Optional;

@Singleton
@Slf4j
public class InstagramInformationService {
    private final InstagramClient instagramClient;
    private final InstagramInformationRepository instagramInformationRepository;

    public InstagramInformationService(InstagramClient instagramClient, InstagramInformationRepository instagramInformationRepository) {
        this.instagramClient = instagramClient;
        this.instagramInformationRepository = instagramInformationRepository;
    }

    public Optional<InstagramInformation> getInstagramInformation(String instagramUserName) {
        return instagramInformationRepository.findById(instagramUserName);
    }

    public InstagramInformation getOrSave(String instagramName) {
        return getInstagramInformation(instagramName).orElseGet(() -> save(instagramName));
    }

    public InstagramInformation save(String instagramName) {
        QueryParams queryParams = instagramClient.getQueryParams(instagramName);
        InstagramInformation instagramInformation =
                InstagramInformation.builder()
                                    .instagramName(instagramName)
                                    .queryHash(queryParams.getQueryHash())
                                    .instagramId(queryParams.getId())
                                    .build();
        return instagramInformationRepository.save(instagramInformation);
    }
}
