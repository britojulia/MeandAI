package br.com.fiap.meandai.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
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
