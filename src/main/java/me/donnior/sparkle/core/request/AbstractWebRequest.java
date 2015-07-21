package me.donnior.sparkle.core.request;

import me.donnior.sparkle.*;
import me.donnior.sparkle.core.ConfigResult;

public abstract class AbstractWebRequest implements WebRequest {

    /**
     * generate session id, or delegate to vendor's session id like servlet
     * @return
     */
    public String getSessionId(){
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Session session() {
//        ConfigResult config = null;
//        if (config.getCustomizedSessionStore() != null ) { //TODO check SessionStoreStrategy is CUSTOMIZED first.
//            SessionStore sessionStore = newSessionStoreInstance(config.getCustomizedSessionStore());
//            return new SessionFacade(sessionStore, this);
//        }
//        if (config.getSessionStoreStrategy().equals(SessionStoreStrategy.COOKIE_STORE)){
//            return new SessionFacade(new CookieBasedSessionStore(), this);
//        }
//        if (config.getSessionStoreStrategy().equals(SessionStoreStrategy.MEMORY_STORE)){
//            return new SessionFacade(new SimpleMemorySessionStore(), this);
//        }
//        if (config.getSessionStoreStrategy().equals(SessionStoreStrategy.VENDOR)){
//            return vendorSession();
//        }
//        return defaultSession();

        /*SessionStore sessionStore = sessionStore();
        if (sessionStore == null){
            return vendorSession();
        }
        return new SessionFacade(sessionStore, this);
        */

        SessionStore sessionStore = sessionStore();
        return new SessionFacade(sessionStore, this);
    }

    /**
     * return application scoped {@link SessionStore}, if not configured in {@link me.donnior.sparkle.config.Config},
     * must return a default one.
     * @return application scoped sessionStore
     */
    protected SessionStore sessionStore(){
        ConfigResult config = null;  //TODO use SessionStoreResolver
        //....
        return null;
    }

    /*
    protected Session defaultSession(){
        return new SessionFacade(new CookieBasedSessionStore(), this);
    }
    */

}