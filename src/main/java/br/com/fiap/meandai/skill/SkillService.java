package br.com.fiap.meandai.skill;

import br.com.fiap.meandai.user.User;
import br.com.fiap.meandai.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;
    private final UserRepository userRepository;


    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public Skill save(Skill skill, Long userId) {
        var user = userRepository.findById(userId).orElseThrow();
        skill.setUser(user);
        return skillRepository.save(skill);
    }

    public int countSkillsByUser(Long userId) {
        return skillRepository.countByUserId(userId);
    }

    public void deleteById(Long id){
        skillRepository.delete(getSkillById(id));
    }


    public Skill getSkillById(Long id){
        return skillRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("skill não encontrado")
        );
    }
}
