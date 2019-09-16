package social.alone.server

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(RetentionPolicy.SOURCE)
annotation class DisplayName(
        /**
         * https://junit.org/junit5/docs/current/user-guide/#writing-tests-display-names
         * junit 5에 올리면 이 에노테이션을 junit5 의 DisplayName 으로 변경합니다.
         */
        val value: String)
