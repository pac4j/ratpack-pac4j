package ratpack.pac4j.internal;

import org.pac4j.core.context.session.SessionStore;
import ratpack.session.SessionData;
import ratpack.util.Exceptions;

/**
 * Specific session store for Ratpack.
 *
 * @author Jerome Leleu
 * @since 3.0.0
 */
public class RatpackSessionStore implements SessionStore<RatpackWebContext> {

    private final SessionData session;

    public RatpackSessionStore(final SessionData session) {
        this.session = session;
    }

    @Override
    public String getOrCreateSessionId(final RatpackWebContext context) {
        return session.getSession().getId();
    }

    @Override
    public Object get(final RatpackWebContext context, final String key) {
        return Exceptions.uncheck(() -> session.get(key, session.getJavaSerializer()).orElse(null));
    }

    @Override
    public void set(final RatpackWebContext context, final String key, final Object value) {
        if (value == null) {
            session.remove(key);
        } else {
            Exceptions.uncheck(() -> session.set(key, value, session.getJavaSerializer()));
        }
    }

    @Override
    public boolean destroySession(final RatpackWebContext context) {
        return false;
    }

    @Override
    public Object getTrackableSession(final RatpackWebContext context) {
        return null;
    }

    @Override
    public SessionStore<RatpackWebContext> buildFromTrackableSession(final RatpackWebContext context, final Object trackableSession) {
        return null;
    }

    @Override
    public boolean renewSession(final RatpackWebContext context) {
        return false;
    }

    public SessionData getSessionData() {
        return session;
    }
}
