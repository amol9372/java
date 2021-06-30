package com.expensetracker.userservice.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Field {

	/*
	 * Name of the Cognito User Pool Attribute
	 */
	public String name() default "";
	boolean required() default false;
	boolean primary() default false;
}
