package fr.helmdefense.model.actions;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to mark methods from
 * {@link ActionListener action listeners} that should
 * be triggered by {@link Action actions}.
 * 
 * <p>Note : Methods marked with this annotation
 * must take exactly one parameter, the action the
 * method listen.
 * 
 * @author	indyteo
 * @see		Action
 * @see		ActionListener
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ActionHandler {}