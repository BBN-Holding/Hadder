package com.bbn.hadder.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Skidder / GregTCLTK
 * @author Hax / Hax6775 / Schlauer_Hax
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface Perms {
    Perm[] value() default {};
}
