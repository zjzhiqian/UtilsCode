package com.hzq.bytebuddy;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.FixedValue;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.isAnnotatedWith;
import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * Created by hzq on 16/11/10.
 */
public class ToStringAgent {

    public static void premain(String arguments, Instrumentation instrumentation) {
        new AgentBuilder.Default()
                .type(isAnnotatedWith(ToString.class))
                .transform((builder, typeDescription, classloader) -> builder.method(named("toString")).intercept(FixedValue.value("transformed")))
                .installOn(instrumentation);
    }
}
