/*
 * @author Hax / Hax6775 / Schlauer_Hax
 */

package com.bbn.hadder.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*
 * @author Skidder / GregTCLTK
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface Perms {
    Perm[] value() default {};
}
