package com.travel.to.travel_to.repository;

import com.travel.to.travel_to.entity.questionnaire.UsabilityQuestionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsabilityQuestionnaireRepository extends JpaRepository<UsabilityQuestionnaire, Long> {
}
