package com.security.jwt.service.jwt;

import com.security.jwt.enums.JwtGenerationEnum;
import com.security.jwt.model.JwtClientDetails;
import com.spring5microservices.common.dto.TokenInformationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

@Service
public class JwtGeneratorService {

    private ApplicationContext applicationContext;
    private JwtClientDetailsService jwtClientDetailsService;

    @Autowired
    public JwtGeneratorService(@Lazy ApplicationContext applicationContext, @Lazy JwtClientDetailsService jwtClientDetailsService) {
        this.applicationContext = applicationContext;
        this.jwtClientDetailsService = jwtClientDetailsService;
    }


    public Optional<TokenInformationDto> generateTokenResponse(String clientId, String username) {
        return JwtGenerationEnum.getByClientId(clientId)
                .map(generator -> applicationContext.getBean(generator.getJwtGeneratorClass()))
                .map(tokenInformation -> {
                    JwtClientDetails clientDetails = jwtClientDetailsService.findByClientId(clientId);

                    // TODO: Include the functionality to add information to TokenInformationDto
                    //TokenRawInformationDto rawInformation = tokenInformation.getTokenInformation(username);

                    return new TokenInformationDto();
                });
    }


/*
// Access token
{
  "exp": 1570638836,
  "jti": "7565113e-85a7-47f6-b184-c9025814bcc4",
  "client_id": "Spring5Microservices"
}
*/


/*
// Refresh token
{
  "ati": "7565113e-85a7-47f6-b184-c9025814bcc4",
  "exp": 1570641536,
  "jti": "b91f82ff-3353-486f-8868-9cc502b6cfb2",
  "client_id": "Spring5Microservices"
}
 */


/*
// FINAL TOKEN
{
    "access_token": "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsImV4cCI6MTU3MDk2MjI0MywiYXV0aG9yaXRpZXMiOlsiVVNFUiIsIkFETUlOIl0sImp0aSI6IjU0ZjczMmEyLTRjODMtNDJjOC1iNjg0LTZkMGU2NGI1MWY4MSIsImNsaWVudF9pZCI6IlNwcmluZzVNaWNyb3NlcnZpY2VzIn0.5--ZTwe52aeO6vzhCo9smXvoFrv1lThoboi8ih-COXGCe9TkGShaWnid_A77Nt8yTbKAuzxqUHuLv0XXblvsMg",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsImF0aSI6IjU0ZjczMmEyLTRjODMtNDJjOC1iNjg0LTZkMGU2NGI1MWY4MSIsImV4cCI6MTU3MDk2NDk0MywianRpIjoiNzY3MGExNjgtOGVkMi00YjNjLTg4NGUtYTExNzM4YjEyYTVhIiwiY2xpZW50X2lkIjoiU3ByaW5nNU1pY3Jvc2VydmljZXMifQ.-YpUKUQsw6qTre6W_Zas31jgVc-JjFyBoLpk-zFi1bj-LaPcqBDosjED9njVz9fs5Rg0_Rlr1aesgzA1GomQBg",
    "expires_in": 899,
    "scope": "read",
    "jti": "54f732a2-4c83-42c8-b684-6d0e64b51f81",
    "additionalInfo": {
        "authorities": [
            "USER",
            "ADMIN"
        ],
        "username": "admin"
    }
}
 */



}
