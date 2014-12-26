package me.donnior.sparkle.route;

/**
 * Interface to config router. You can have multi RouteModules separated in different files,
 * the sparkle framework will scan and install them all.
 */
public interface RouteModule {

    /**
     *
     * config router.
     *
     */
    void config(Router router);

}
