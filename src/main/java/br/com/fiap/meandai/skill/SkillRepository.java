package br.com.fiap.meandai.skill;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill,Long> {
    int countByUserId(Long userId);
}
