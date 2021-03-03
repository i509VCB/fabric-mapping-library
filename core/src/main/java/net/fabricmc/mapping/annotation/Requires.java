package net.fabricmc.mapping.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import net.fabricmc.mapping.MappingFormat;

/**
 * Specifies that a specific method in the mapping visitor will only be called if a specific feature is enabled.
 */
@Documented
@Target({ElementType.METHOD, ElementType.PARAMETER})
public @interface Requires {
	/**
	 * Features that must be supported in order for a method to a visited.
	 *
	 * @return an array of features
	 */
	MappingFormat.Feature[] value();
}
