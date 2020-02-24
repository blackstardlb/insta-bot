package nl.blackstardlb.insta.bot.service;

import nl.blackstardlb.insta.bot.data.entities.InstagramInformation;
import nl.blackstardlb.insta.bot.data.entities.LastInstagramPost;
import nl.blackstardlb.insta.bot.data.instagram.InstagramClient;
import nl.blackstardlb.insta.bot.data.instagram.models.request.QueryParams;
import nl.blackstardlb.insta.bot.data.instagram.models.response.graphql.*;
import nl.blackstardlb.insta.bot.exceptions.NodeNotFoundException;

import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class NewPostService {
    private final InstagramClient instagramClient;

    public NewPostService(InstagramClient instagramClient) {
        this.instagramClient = instagramClient;
    }

    public LastInstagramPost getLatestInstagramPost(InstagramInformation instagramInformation) {
        QueryParams queryParams = QueryParams.builder()
                                             .queryHash(instagramInformation.getQueryHash())
                                             .id(instagramInformation.getInstagramId())
                                             .build();
        InstagramQueryResponse queryResponse = instagramClient.getQueryResponse(queryParams);
        Optional<Node> nodeOptional = getNode(queryResponse);
        if (!nodeOptional.isPresent()) throw new NodeNotFoundException();
        Node node = nodeOptional.get();
        return LastInstagramPost.builder()
                                .createdAt(node.getUploadedOn())
                                .id(node.getShortcode())
                                .build();
    }

    private Optional<Node> getNode(InstagramQueryResponse instagramQueryResponse) {
        return instagramQueryResponse.getData()
                                     .flatMap(Data::getUser)
                                     .flatMap(User::getTimeLineMedia)
                                     .flatMap(timeLineMedia -> timeLineMedia.getEdges().stream().findFirst())
                                     .flatMap(NodeWrapper::getNode);
    }
}
