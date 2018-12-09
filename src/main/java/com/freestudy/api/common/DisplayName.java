package com.freestudy.api.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface DisplayName {
  /**
   * https://junit.org/junit5/docs/current/user-guide/#writing-tests-display-names
   * junit 5에 올리면 이 에노테이션을 junit5 의 DisplayName 으로 변경합니다.
   */
  String value();
}
