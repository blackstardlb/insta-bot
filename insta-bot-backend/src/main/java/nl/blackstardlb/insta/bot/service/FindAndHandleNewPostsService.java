package nl.blackstardlb.insta.bot.service;

import lombok.extern.slf4j.Slf4j;
import nl.blackstardlb.insta.bot.data.entities.LastInstagramPost;
import nl.blackstardlb.insta.bot.data.entities.NotificationRequest;

import javax.inject.Singleton;
import java.util.List;

@Singleton
@Slf4j
public class FindAndHandleNewPostsService {
    private final NewPostService newPostService;
    private final NotificationRequestService notificationRequestService;
    private final NotificationDispatcherService notificationDispatcherService;

    public FindAndHandleNewPostsService(NewPostService newPostService, NotificationRequestService notificationRequestService, NotificationDispatcherService notificationDispatcherService) {
        this.newPostService = newPostService;
        this.notificationRequestService = notificationRequestService;
        this.notificationDispatcherService = notificationDispatcherService;
    }

    public void findAndHandleNewPosts() {
        List<NotificationRequest> notificationRequests = notificationRequestService.getGetEnabledNotificationRequests();
        log.debug("notificationRequests.size() = " + notificationRequests.size());
        notificationRequests.forEach(notificationRequest -> {
            LastInstagramPost latestInstagramPost = newPostService.getLatestInstagramPost(notificationRequest.getInstagramInformation());
            if (isNewPost(notificationRequest, latestInstagramPost)) {
                log.info("New Post : " + latestInstagramPost);
                notificationDispatcherService.sendNotification(notificationRequest, latestInstagramPost);
            } else {
                log.info("Old post");
            }
        });
    }

    private boolean isNewPost(NotificationRequest notificationRequest, LastInstagramPost lastInstagramPost) {
        if (lastInstagramPost == null) return false;
        if (notificationRequest.getLastInstagramPost() == null) return true;
        return lastInstagramPost.getCreatedAt().isAfter(notificationRequest.getLastInstagramPost().getCreatedAt());
    }
}
