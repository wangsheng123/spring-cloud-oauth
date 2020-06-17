package com.ws.icloud.auth;

import com.ws.icloud.auth.json.Jackson2Mapper;
import com.ws.icloud.auth.json.JacksonMapper;
import com.ws.icloud.auth.json.JsonMapper;
import com.ws.icloud.auth.json.NotSupportedJsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.util.ClassUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Slf4j
public   class ClientDetailsRowMapper implements RowMapper<ClientDetails> {
    private JsonMapper mapper = createJsonMapper();

    public ClientDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        DefaultClientDetails details = new DefaultClientDetails(rs.getString(1), rs.getString(3), rs.getString(4),
                rs.getString(5), rs.getString(7), rs.getString(6));
        details.setClientSecret(rs.getString(2));
        if (rs.getObject(8) != null) {
            details.setAccessTokenValiditySeconds(rs.getInt(8));
        }
        if (rs.getObject(9) != null) {
            details.setRefreshTokenValiditySeconds(rs.getInt(9));
        }
        String json = rs.getString(10);
        if (json != null) {
            try {
                @SuppressWarnings("unchecked")
                Map<String, Object> additionalInformation = mapper.read(json, Map.class);
                details.setAdditionalInformation(additionalInformation);
            }
            catch (Exception e) {
                log.warn("Could not decode JSON for additional information: " + details, e);
            }
        }
        String scopes = rs.getString(11);

        long ifLimit = rs.getLong(12) ;
        details.setIf_limit(ifLimit);

        long limitCount = rs.getLong(13) ;
        details.setLimit_count(limitCount);
        if (scopes != null) {
            details.setAutoApproveScopes(org.springframework.util.StringUtils.commaDelimitedListToSet(scopes));
        }
        return details;
    }


    /**
     * json process
     * @return
     */
    private static JsonMapper createJsonMapper() {
        if (ClassUtils.isPresent("org.codehaus.jackson.map.ObjectMapper", null)) {
            return new JacksonMapper();
        }
        else if (ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", null)) {
            return new Jackson2Mapper();
        }
        return new NotSupportedJsonMapper();
    }
}
