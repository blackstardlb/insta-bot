package nl.blackstardlb.insta.bot.service;

import lombok.extern.slf4j.Slf4j;
import nl.blackstardlb.insta.bot.data.entities.InstagramInformation;
import nl.blackstardlb.insta.bot.data.entities.LastInstagramPost;
import nl.blackstardlb.insta.bot.data.entities.NotificationRequest;
import nl.blackstardlb.insta.bot.data.repositories.NotificationRequestRepository;
import org.apache.commons.collections4.IterableUtils;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Singleton
@Slf4j
public class NotificationRequestService {
    private final InstagramInformationService instagramInformationService;
    private final LastInstagramPostService lastInstagramPostService;
    private final NotificationRequestRepository notificationRequestRepository;
    private final NewPostService newPostService;

    public NotificationRequestService(InstagramInformationService instagramInformationService, LastInstagramPostService lastInstagramPostService, NotificationRequestRepository notificationRequestRepository, NewPostService newPostService) {
        this.instagramInformationService = instagramInformationService;
        this.lastInstagramPostService = lastInstagramPostService;
        this.notificationRequestRepository = notificationRequestRepository;
        this.newPostService = newPostService;
    }

    public List<NotificationRequest> getNotificationRequests() {
        return IterableUtils.toList(notificationRequestRepository.findAll());
    }

    public List<NotificationRequest> getGetEnabledNotificationRequests() {
        return notificationRequestRepository.findByIsEnabledTrue();
    }

    @Transactional
    public NotificationRequest save(NotificationRequest notificationRequest) {
        InstagramInformation instagramInformation = notificationRequest.getInstagramInformation();

        Optional<InstagramInformation> instagramInformationOptional = instagramInformationService.getInstagramInformation(
                instagramInformation.getInstagramName());
        if (!instagramInformationOptional.isPresent()) {
            log.info("new instagram information");
            instagramInformation = instagramInformationService.save(instagramInformation.getInstagramName());
        } else {
            log.info("reusing instagram information");
            instagramInformation = instagramInformationOptional.get();
        }

        LastInstagramPost lastInstagramPost = newPostService.getLatestInstagramPost(instagramInformation);
        if (notificationRequest.getLastInstagramPost() == null || !lastInstagramPost.getId()
                                                                                    .equals(notificationRequest.getLastInstagramPost()
                                                                                                               .getId())) {
            log.info("new post");
            lastInstagramPost = lastInstagramPostService.getOrSave(lastInstagramPost);
        } else {
            log.info("same post");
            lastInstagramPost = notificationRequest.getLastInstagramPost();
        }

        notificationRequest.setInstagramInformation(instagramInformation);
        notificationRequest.setLastInstagramPost(lastInstagramPost);
        return notificationRequestRepository.save(notificationRequest);
    }

    @Transactional
    public NotificationRequest updateLastInstagramPost(NotificationRequest notificationRequest, LastInstagramPost lastInstagramPost) {
        lastInstagramPost = lastInstagramPostService.getOrSave(lastInstagramPost);
        notificationRequest = notificationRequestRepository.findById(notificationRequest.getId())
                                                           .orElseThrow(() -> new RuntimeException(
                                                                   "No notificationRequest"));
        notificationRequest.setLastInstagramPost(lastInstagramPost);
        notificationRequestRepository.save(notificationRequest);
        return notificationRequestRepository.findById(notificationRequest.getId()).orElse(null);
    }

    public Optional<NotificationRequest> getByDiscordId(String discordId) {
        return notificationRequestRepository.findByDiscordServerId(discordId);
    }
}
