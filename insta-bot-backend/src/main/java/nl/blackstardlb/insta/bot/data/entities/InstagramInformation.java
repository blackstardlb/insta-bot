package nl.blackstardlb.insta.bot.data.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class InstagramInformation {
    @Id
    private String instagramName;

    @NotNull
    @NotBlank
    private String queryHash;

    @NotNull
    @NotBlank
    private Long instagramId;
}
