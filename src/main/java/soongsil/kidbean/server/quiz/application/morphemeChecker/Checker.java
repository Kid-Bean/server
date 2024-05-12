package soongsil.kidbean.server.quiz.application.morphemeChecker;

import static java.lang.Boolean.TRUE;
import static soongsil.kidbean.server.quiz.application.vo.type.MorphemeCheckEnum.USE_EC_1;
import static soongsil.kidbean.server.quiz.application.vo.type.MorphemeCheckEnum.USE_EC_2;
import static soongsil.kidbean.server.quiz.application.vo.type.MorphemeCheckEnum.USE_ENDING_1;
import static soongsil.kidbean.server.quiz.application.vo.type.MorphemeCheckEnum.USE_ENDING_2;
import static soongsil.kidbean.server.quiz.application.vo.type.MorphemeCheckEnum.USE_ENDING_3;
import static soongsil.kidbean.server.quiz.application.vo.type.MorphemeCheckEnum.USE_JKS_1;
import static soongsil.kidbean.server.quiz.application.vo.type.MorphemeCheckEnum.USE_JKS_2;
import static soongsil.kidbean.server.quiz.application.vo.type.MorphemeCheckEnum.USE_J_1;
import static soongsil.kidbean.server.quiz.application.vo.type.MorphemeCheckEnum.USE_J_2;
import static soongsil.kidbean.server.quiz.application.vo.type.MorphemeCheckEnum.USE_MA_1;
import static soongsil.kidbean.server.quiz.application.vo.type.MorphemeCheckEnum.USE_MA_2;
import static soongsil.kidbean.server.quiz.application.vo.type.MorphemeCheckEnum.USE_NNB_1;
import static soongsil.kidbean.server.quiz.application.vo.type.MorphemeCheckEnum.USE_NNB_2;
import static soongsil.kidbean.server.quiz.application.vo.type.MorphemeCheckEnum.USE_NOUN;
import static soongsil.kidbean.server.quiz.application.vo.type.MorphemeCheckEnum.USE_NP_1;
import static soongsil.kidbean.server.quiz.application.vo.type.MorphemeCheckEnum.USE_NP_2;
import static soongsil.kidbean.server.quiz.application.vo.type.MorphemeCheckEnum.USE_VA_1;
import static soongsil.kidbean.server.quiz.application.vo.type.MorphemeCheckEnum.USE_VV_VA_1;
import static soongsil.kidbean.server.quiz.application.vo.type.MorphemeCheckEnum.USE_VV_VA_2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.quiz.application.vo.type.MorphemeCheckEnum;

/**
 * 명사 checker
 */
@Service
@Transactional
class NounChecker implements MorphemeChecker {
    @Override
    public void analyzeMorpheme(Map<MorphemeCheckEnum, Boolean> checkList, String type,
                                String lemma) {
        updateCheckListTrue(checkList, USE_NOUN);

        if (type.equals("NNB")) {
            if (lemma.equals("것")) {
                updateCheckListTrue(checkList, USE_NNB_1);
            } else {
                updateCheckListTrue(checkList, USE_NNB_2);
            }
        }
    }
}

/**
 * 대명사 checker
 */
@Service
@Transactional
class NPChecker implements MorphemeChecker {
    @Override
    public void analyzeMorpheme(Map<MorphemeCheckEnum, Boolean> checkList, String type,
                                String lemma) {
        if (lemma.equals("나") || lemma.equals("저")) {
            updateCheckListTrue(checkList, USE_NP_1);
        } else {
            updateCheckListTrue(checkList, USE_NP_2);
        }
    }
}

/**
 * 주격조사 checker
 */
@Service
@Transactional
class JKSChecker implements MorphemeChecker {
    @Override
    public void analyzeMorpheme(Map<MorphemeCheckEnum, Boolean> checkList, String type,
                                String lemma) {
        if (lemma.equals("가")) {
            updateCheckListTrue(checkList, USE_JKS_1);
        } else {
            updateCheckListTrue(checkList, USE_JKS_2);
        }
    }
}

/**
 * 동사 checker
 */
@Service
@Transactional
class VVChecker implements MorphemeChecker {
    @Override
    public void analyzeMorpheme(Map<MorphemeCheckEnum, Boolean> checkList, String type,
                                String lemma) {
        updateCheckListTrue(checkList, USE_VV_VA_1);

        if (lemma.length() > 2) {
            updateCheckListTrue(checkList, USE_VV_VA_2);
        }
    }
}

@Service
@Transactional
class VAChecker implements MorphemeChecker {
    private final List<String> VA_1_LIST = Arrays.asList("이렇", "저렇", "어떻");

    @Override
    public void analyzeMorpheme(Map<MorphemeCheckEnum, Boolean> checkList, String type,
                                String lemma) {
        updateCheckListTrue(checkList, USE_VV_VA_1);

        if (lemma.length() > 2) {
            updateCheckListTrue(checkList, USE_VV_VA_2);
        }

        if (VA_1_LIST.contains(lemma)) {
            updateCheckListTrue(checkList, USE_VA_1);
        }
    }
}

@Service
@Transactional
class ECChecker implements MorphemeChecker {
    @Override
    public void analyzeMorpheme(Map<MorphemeCheckEnum, Boolean> checkList, String type,
                                String lemma) {
        if ("고".equals(lemma)) {
            updateCheckListTrue(checkList, USE_EC_1);
        } else {
            updateCheckListTrue(checkList, USE_EC_2);
        }
    }
}

@Service
@Transactional
class JXJCChecker implements MorphemeChecker {
    private final List<String> JC_2_LIST = Arrays.asList("랑", "하고", "와", "고");

    @Override
    public void analyzeMorpheme(Map<MorphemeCheckEnum, Boolean> checkList, String type,
                                String lemma) {

        updateCheckListTrue(checkList, USE_J_1);

        if ("JC".equals(type) && JC_2_LIST.contains(lemma)) {
            updateCheckListTrue(checkList, USE_J_2);
        }
    }
}

@Service
@Transactional
class MAChecker implements MorphemeChecker {
    @Override
    public void analyzeMorpheme(Map<MorphemeCheckEnum, Boolean> checkList, String type,
                                String lemma) {
        updateCheckListTrue(checkList, USE_MA_1);

        if (lemma.length() > 2) {
            updateCheckListTrue(checkList, USE_MA_2);
        }
    }
}

@Service
@Transactional
class EndingChecker implements MorphemeChecker {
    private final List<String> ENDING_1_LIST = Arrays.asList("야", "자");
    private final List<String> ENDING_3_LIST = Arrays.asList("어요", "요");
    @Override
    public void analyzeMorpheme(Map<MorphemeCheckEnum, Boolean> checkList, String type,
                                String lemma) {

        if (ENDING_1_LIST.contains(lemma)) {
            updateCheckListTrue(checkList, USE_ENDING_1);
        } else {
            updateCheckListTrue(checkList, USE_ENDING_2);
        }

        if (ENDING_3_LIST.contains(lemma)) {
            updateCheckListTrue(checkList, USE_ENDING_3);
        }
    }
}

