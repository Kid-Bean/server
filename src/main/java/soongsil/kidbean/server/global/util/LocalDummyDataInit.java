package soongsil.kidbean.server.global.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 로컬 환경에서 더미 데이터를 넣을 때 사용하는 annotation
 *
 * @Profile이 dev인 경우 사용
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Profile("dev")
@Transactional
@Component
public @interface LocalDummyDataInit {
}
