package com.overlake.ftc.ftcrobothub.webserver.routing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import fi.iki.elonen.NanoHTTPD.Method;

@Retention(RetentionPolicy.RUNTIME) @Target(ElementType.METHOD)
public @interface AddRoute {
    String uri() default "/";
    Method method() default Method.GET;
}
