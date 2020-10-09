package com.lyc.city.constant;

/**
 * @author lyc
 * @date 2020/9/30 15:05
 */
public interface AuthConstants {

    /**
     * JWT存储权限前缀
     */
    String AUTHORITY_PREFIX = "ROLE_";

    /**
     * JWT存储权限属性
     */
    String AUTHORITY_CLAIM_NAME = "authorities";

    /**
     * 认证信息Http请求头
     */
    String JWT_TOKEN_HEADER = "Authorization";

    /**
     * JWT令牌前缀
     */
    String JWT_TOKEN_PREFIX = "Bearer ";

    /**
     * JWT载体key
     */
    String JWT_PAYLOAD_KEY = "payload";

    /**
     * Redis缓存权限规则key
     */
    String RESOURCE_ROLES_KEY = "auth:resourceRoles";


    String TOKEN_BLACKLIST_PREFIX = "auth:token:blacklist:";

    String CLIENT_DETAILS_FIELDS = "client_id, CONCAT('{noop}',client_secret) as client_secret, resource_ids, scope, "
            + "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
            + "refresh_token_validity, additional_information, autoapprove";
    String BASE_CLIENT_DETAILS_SQL = "select " + CLIENT_DETAILS_FIELDS + " from oauth_client_details";
    String FIND_CLIENT_DETAILS_SQL = BASE_CLIENT_DETAILS_SQL + " order by client_id";
    String SELECT_CLIENT_DETAILS_SQL = BASE_CLIENT_DETAILS_SQL + " where client_id = ?";

    String BCRYPT = "{bcrypt}";

    String JWT_USER_ID_KEY = "id";

    String JWT_CLIENT_ID_KEY = "client_id";
}
