package org.sods.common.logging;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.Date;

public class CustomLayout extends PatternLayout {

    public CustomLayout() {
        this.setPattern("%d{yyyy-MM-dd HH:mm:ss.SSS} %level %logger{36} - %msg%n");
    }
    @Override
    public String doLayout(ILoggingEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append(CustomAppender.formatDate(new Date(event.getTimeStamp()))).append(" ");
        sb.append(event.getLevel()).append(" ");
        sb.append(event.getLoggerName()).append(" - ");
        sb.append(event.getFormattedMessage());
        sb.append(System.getProperty("line.separator"));
        return sb.toString();
    }
}
