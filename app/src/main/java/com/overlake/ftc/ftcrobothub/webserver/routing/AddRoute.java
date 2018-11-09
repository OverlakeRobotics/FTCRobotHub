package com.overlake.ftc.ftcrobothub.webserver.routing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import fi.iki.elonen.NanoHTTPD.Method;

@Target(ElementType.METHOD)
public @interface AddRoute {
    String uri() default "/";
    Method method() default Method.GET;
}
