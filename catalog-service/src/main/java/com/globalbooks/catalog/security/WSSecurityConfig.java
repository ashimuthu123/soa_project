package com.globalbooks.catalog.security;

import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.springframework.stereotype.Component;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class WSSecurityConfig {

    // In-memory user store (in production, this would be a database)
    private static final Map<String, String> userStore = new HashMap<>();
    
    static {
        userStore.put("admin", "admin123");
        userStore.put("user", "user123");
        userStore.put("service", "service123");
    }

    /**
     * Password callback handler for WS-Security UsernameToken validation
     */
    public static class PasswordCallbackHandler implements CallbackHandler {
        
        @Override
        public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
            for (Callback callback : callbacks) {
                if (callback instanceof WSPasswordCallback) {
                    WSPasswordCallback pc = (WSPasswordCallback) callback;
                    String username = pc.getIdentifier();
                    String password = userStore.get(username);
                    
                    if (password != null) {
                        pc.setPassword(password);
                    }
                }
            }
        }
    }

    /**
     * Get password callback handler instance
     */
    public static CallbackHandler getPasswordCallbackHandler() {
        return new PasswordCallbackHandler();
    }

    /**
     * Validate user credentials
     */
    public static boolean validateUser(String username, String password) {
        String storedPassword = userStore.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }

    /**
     * Add new user to the store
     */
    public static void addUser(String username, String password) {
        userStore.put(username, password);
    }

    /**
     * Remove user from the store
     */
    public static void removeUser(String username) {
        userStore.remove(username);
    }
}
