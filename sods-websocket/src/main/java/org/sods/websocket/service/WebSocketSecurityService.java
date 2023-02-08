package org.sods.websocket.service;

public interface WebSocketSecurityService {
    String getUserName(String jsonString);
    Boolean checkPermission(String jsonString, String requirement);
}
