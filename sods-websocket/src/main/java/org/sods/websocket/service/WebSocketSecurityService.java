package org.sods.websocket.service;

import java.security.Principal;

public interface WebSocketSecurityService {
    String getUserID(Principal principal);
    Boolean checkPermission(Principal principal, String requirement);
}
