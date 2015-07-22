package me.donnior.sparkle.core.request;

import me.donnior.sparkle.Session;
import me.donnior.sparkle.WebRequest;

/**
 * a session facade which will delegate session operation to application configured {@link SessionStore}
 */
public class SessionFacade implements Session{

    private final SessionStore store;
    private final WebRequest request;

    public SessionFacade(SessionStore store, WebRequest request){
        this.store = store;
        this.request = request;
    }

    @Override
    public String id() {
        return this.request.getSessionId();
    }

    @Override
    public Session set(String name, Object obj) {
        this.store.set(this.request, name, obj);
        return this;
    }

    @Override
    public <T> T get(String name) {
        return (T)this.store.get(this.request, name);
    }

    public SessionStore sessionStore(){
        return this.store;
    }

    public WebRequest webRequest(){
        return this.request;
    }

}
