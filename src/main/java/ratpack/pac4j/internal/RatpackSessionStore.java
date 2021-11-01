package ratpack.pac4j.internal;

import java.util.Optional;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import ratpack.session.SessionData;
import ratpack.util.Exceptions;

/**
 * Specific session store for Ratpack.
 *
 * @author Jerome Leleu
 * @since 3.0.0
 */
public class RatpackSessionStore implements SessionStore {

    private final SessionData session;

    public RatpackSessionStore(final SessionData session) {
        this.session = session;
    }

    @Override
    public Optional<String> getSessionId(final WebContext context, boolean createSession) {
        return Optional.of(session.getSession().getId());
    }

    @SuppressWarnings("unchecked")
    @Override
    public Optional<Object> get(final WebContext context, final String key) {
        return (Optional<Object>) Exceptions.uncheck(() -> session.get(key, session.getJavaSerializer()));
    }

    @Override
    public void set(final WebContext context, final String key, final Object value) {
        if (value == null) {
            session.remove(key);
        } else {
            Exceptions.uncheck(() -> session.set(key, value, session.getJavaSerializer()));
        }
    }

    @Override
    public boolean destroySession(final WebContext context) {
        return false;
    }

    @Override
    public Optional<Object> getTrackableSession(final WebContext context) {
        return Optional.empty();
    }

    @Override
    public Optional<SessionStore> buildFromTrackableSession(final WebContext context, final Object trackableSession) {
        return Optional.empty();
    }

    @Override
    public boolean renewSession(final WebContext context) {
        return false;
    }

    public SessionData getSessionData() {
        return session;
    }
}
