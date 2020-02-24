package nl.blackstardlb.insta.bot.data.instagram.parsers;

import org.apache.commons.lang3.StringUtils;

import javax.inject.Singleton;

@Singleton
public class QueryHashParser {
    public String getQueryHashFromScript(String js) {
        String open = "pagination},queryId:\"";
        return StringUtils.substringBetween(
                js.substring(StringUtils.ordinalIndexOf(js, open, 3)),
                open,
                "\",queryParams:function("
        );
    }
}
