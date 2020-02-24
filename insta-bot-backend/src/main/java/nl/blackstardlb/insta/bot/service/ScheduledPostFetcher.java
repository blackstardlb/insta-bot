package nl.blackstardlb.insta.bot.service;

import io.micronaut.context.annotation.Requires;
import io.micronaut.scheduling.annotation.Scheduled;

import javax.inject.Singleton;

@Singleton
@Requires(notEnv = "test")
public class ScheduledPostFetcher {
    private final FindAndHandleNewPostsService findAndHandleNewPostsService;

    public ScheduledPostFetcher(FindAndHandleNewPostsService findAndHandleNewPostsService) {
        this.findAndHandleNewPostsService = findAndHandleNewPostsService;
    }

    @Scheduled(fixedRate = "${instagram.queryRate}")
    public void lookForNewPost() {
        findAndHandleNewPostsService.findAndHandleNewPosts();
    }
}
