//package unical.enterprise.jokibackend.Utility;
//
//import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
//import org.keycloak.OAuth2Constants;
//import org.keycloak.admin.client.Keycloak;
//import org.keycloak.admin.client.KeycloakBuilder;
//
//public class KeycloakManager {
//
//    static Keycloak keycloak = null;
//
//    final static String serverUrl = "http://localhost:8080/";
//    public final static String realm = "JokiRealm";
//    final static String clientId = "JokiBackend";
//    final static String clientSecret = "BAAHnEU37J7LX0Do6sRcsDN9IZNVs20g";
////    final static String userName = "admin";
////    final static String password = "admin";
//
//    public KeycloakManager() {
//    }
//
//    public static Keycloak getInstance(){
//        if(keycloak == null){
//            keycloak = KeycloakBuilder.builder()
//                    .serverUrl(serverUrl)
//                    .realm(realm)
//                    .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
////                    .username(userName)
////                    .password(password)
//                    .clientId(clientId)
//                    .clientSecret(clientSecret)
//                    .resteasyClient(
//                            new ResteasyClientBuilder().connectionPoolSize(10).build()
//                        ).build();
//        }
//        return keycloak;
//    }
//
//    public static String getUserToken(String username, String password){
//        var tmp = KeycloakBuilder.builder()
//                .serverUrl(serverUrl)
//                .realm(realm)
//                .grantType(OAuth2Constants.PASSWORD)
//                .username(username)
//                .password(password)
//                .clientId(clientId)
//                .clientSecret(clientSecret)
//                .resteasyClient(
//                        new ResteasyClientBuilder().connectionPoolSize(10).build()
//                ).build();
//
//        String token = tmp.tokenManager().getAccessTokenString();
//        tmp.close();
//
//        return token;
//    }
//}