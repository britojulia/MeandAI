package br.com.fiap.meandai.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User register(OAuth2User principal) {
        Map<String, Object> attrs = principal.getAttributes();

        String email = (String) attrs.get("email");
        String name = (String) attrs.get("name");
        String avatar = (String) attrs.get("avatar_url");

        if (email == null) {
            throw new RuntimeException("Email não disponível");
        }

        var user = userRepository.findByEmail(email);

        String emailTemp = email;

        return user.orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(emailTemp);
            newUser.setName(name);
            newUser.setAvatarUrl(avatar);
            return userRepository.save(newUser);
        });
    }


    public User save(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("usuario não encontrado")
        );
    }

}
