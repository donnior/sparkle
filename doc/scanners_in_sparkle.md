Scanner means you just need put your code under classpath and it will be discovered automatically.

There a three major scanners in sparkle.

## Application Config Scanner

This scanner will scan class which is subclass of `Application`. If you want to config framework, just need one implementation on classpath and only one.

## Controllers Scanner

This scanner will scan all controller class which is subclass of `ApplicationController` or annotated with `Controller`.

## Route Module Scanner

This scanner will scan class which is sublcass of `RouteModule`. You can define multi route modules within different classes.
