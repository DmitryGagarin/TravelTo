package com.travel.to.travel_to.entity.questionnaire;

import com.travel.to.travel_to.entity.common.UuidAbleEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

//        **SUS questionnaire**
//1.  I think that I would like to use the system frequently
//2.  I found the system unnecessarily complex
//3.  I thought the system was easy to use
//4.  I think that I would need the support of a technical person to use this system
//5.  I found the various functions of this system were well integrated
//6.  I thought there was too much inconsistency in this system
//7.  I would imagine that most people would learn to use this system very quickly
//8.  I found the system very cumbersome to use
//9.  I felt very confident using the system
//10. I needed to learn a lot of things before I could get going with this system

@Entity
@Table(name = "usability_questionnaire")
public class UsabilityQuestionnaire extends UuidAbleEntity {
    private long answererId;
    private int q1;
    private int q2;
    private int q3;
    private int q4;
    private int q5;
    private int q6;
    private int q7;
    private int q8;
    private int q9;
    private int q10;

    public long getAnswererId() {
        return answererId;
    }

    public UsabilityQuestionnaire setAnswererId(long answererId) {
        this.answererId = answererId;
        return this;
    }

    public int getQ1() {
        return q1;
    }

    public UsabilityQuestionnaire setQ1(int q1) {
        this.q1 = q1;
        return this;
    }

    public int getQ2() {
        return q2;
    }

    public UsabilityQuestionnaire setQ2(int q2) {
        this.q2 = q2;
        return this;
    }

    public int getQ3() {
        return q3;
    }

    public UsabilityQuestionnaire setQ3(int q3) {
        this.q3 = q3;
        return this;
    }

    public int getQ4() {
        return q4;
    }

    public UsabilityQuestionnaire setQ4(int q4) {
        this.q4 = q4;
        return this;
    }

    public int getQ5() {
        return q5;
    }

    public UsabilityQuestionnaire setQ5(int q5) {
        this.q5 = q5;
        return this;
    }

    public int getQ6() {
        return q6;
    }

    public UsabilityQuestionnaire setQ6(int q6) {
        this.q6 = q6;
        return this;
    }

    public int getQ7() {
        return q7;
    }

    public UsabilityQuestionnaire setQ7(int q7) {
        this.q7 = q7;
        return this;
    }

    public int getQ8() {
        return q8;
    }

    public UsabilityQuestionnaire setQ8(int q8) {
        this.q8 = q8;
        return this;
    }

    public int getQ9() {
        return q9;
    }

    public UsabilityQuestionnaire setQ9(int q9) {
        this.q9 = q9;
        return this;
    }

    public int getQ10() {
        return q10;
    }

    public UsabilityQuestionnaire setQ10(int q10) {
        this.q10 = q10;
        return this;
    }
}
