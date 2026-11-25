package notificator.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import notificator.repository.ApiKeyRepository;
import notificator.util.KeyHasher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class ApiKeyAuthFilter extends OncePerRequestFilter {
    private final ApiKeyRepository apiKeys;

    public ApiKeyAuthFilter(ApiKeyRepository apiKeys) {
        this.apiKeys = apiKeys;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String key = request.getHeader("X-API-Key");
        if (key != null && !key.isBlank()) {
            apiKeys.findByKeyHashAndRevokedAtIsNull(KeyHasher.sha256(key)).ifPresent(apiKey -> {
                var auth = new UsernamePasswordAuthenticationToken(
                        apiKey.getTenantId(), null, List.of(new SimpleGrantedAuthority("ROLE_TENANT")));
                SecurityContextHolder.getContext().setAuthentication(auth);
            });
        }
        chain.doFilter(request, response);
    }
}