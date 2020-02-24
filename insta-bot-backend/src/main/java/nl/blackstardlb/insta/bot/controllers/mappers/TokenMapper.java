package nl.blackstardlb.insta.bot.controllers.mappers;

import nl.blackstardlb.insta.bot.controllers.dto.TokenDTO;
import nl.blackstardlb.insta.bot.data.discord.TokenResponseBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TokenMapper {
    TokenMapper INSTANCE = Mappers.getMapper(TokenMapper.class);

    TokenDTO toTokenDTO(TokenResponseBody tokenResponseBody);
}
