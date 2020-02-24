package nl.blackstardlb.insta.bot.data.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class NotificationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Boolean isEnabled;

    @NotBlank
    @NotNull
    private String discordChanelId;

    @NotBlank
    @NotNull
    private String discordServerId;

    @NotNull
    @ManyToOne
    private InstagramInformation instagramInformation;

    @OneToOne
    private LastInstagramPost lastInstagramPost;
}

