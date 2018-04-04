package com.cafe24.security;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Auth {
	
	public enum Role {GUEST, USER}
	
	public Role role() default Role.GUEST;
	
	//default 값은 user로
	// @Auth(value="admin") -> value 값을 admin으로 설정 하겠다.
	//public String value() default "user";
	//public int test() default 1;
	
	
}
