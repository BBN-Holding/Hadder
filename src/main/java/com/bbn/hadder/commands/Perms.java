/*
 * @author Hax / Hax6775 / Schlauer_Hax
 */

package com.bbn.hadder.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Perms {
    Perm[] value() default {};
}
