Sparkle support several argument type for your action method.

# Built-In types

Sparkle support these built-in parameter types

## String

Pass string parameters from request.

__You should use `@Param` or `@PathVarialb` when use `String` as parameter type, because currently we can't get parameter name from java method directly(though java 8 has parameter in reflection, but it's disabled by defaul)__

## Primitive type and wrap type

You can pass `int`, `double`, `float`, or their wrap type `Integer`, `Float`, `Double`. Sparkle will automatically convert param from string to correct type.


## WebRequest

The sparkle's core interface `WebRequest` can be passed as parameter

## WebResponse

The sparkle's core interface `WebResponse` can be passed as parameter

## Params

`Params` represent all params from current request.

# Vendor types

You can also pass some vender supported types as parameter.

## HttpServletRequest

If you run sparkle with a servlet container, you can pass `HttpServletRequest` as parameter.

## HttpServletResponse

If you run sparkle with a servlet container, you can pass `HttpServletResponse` as parameter.

## FullHttpRequest

If you run sparkle with a servlet container, you can pass netty's `FullHttpRequest` as parameter.

