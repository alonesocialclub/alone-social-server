package social.alone.server.auth.email;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import social.alone.server.auth.oauth2.user.TokenProvider;

@Service
@RequiredArgsConstructor
public class AuthTokenGenerator {

    private final AuthenticationManager authenticationManager;

    private final TokenProvider tokenProvider;

    public String byEmailPassword(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        String token = tokenProvider.createToken(authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return token;
    }
}
