package marketplace.security;

import marketplace.repositories.exceptions.NotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManagement
{
    // maps unique session id to username
    private Map<String, String> sessions;

    // This is a simple implementation for managing sessions for users
    // It creates a session for a user when he logs in and shuts down the session when logged out,
    // it does not support session timeout

    public SessionManagement()
    {
        this.sessions = new HashMap<>();
    }

    public String login(String username, String password) throws NotFoundException
    {
        if (this.sessions.containsValue(username))
        {
            // remove old session and create a new one
            this.sessions.values().remove(username);
        }

        String session = UUID.randomUUID().toString();
        this.sessions.put(session, username);

        return session;

    }

    public void logout(String session)
    {
        if(this.sessions.containsKey(session))
        {
            this.sessions.remove(session);
        }
    }

    public boolean isValidSession(String session)
    {
        if(this.sessions.containsKey(session)){
            return true;
        }
        else {
            return false;
        }
    }

    public String getUsernameBySession(String session)
    {
        return this.sessions.get(session);
    }
}
