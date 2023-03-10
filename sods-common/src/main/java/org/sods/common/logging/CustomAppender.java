package org.sods.common.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomAppender extends AppenderBase<ILoggingEvent> {

    private Layout<ILoggingEvent> layout;

    @Override
    public void start() {
        if (this.layout == null) {
            LayoutWrappingEncoder<ILoggingEvent> encoder = new LayoutWrappingEncoder<>();
            encoder.setLayout(new CustomLayout());
            this.layout = encoder.getLayout();
        }
        super.start();
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
        String message = this.layout.doLayout(eventObject);
        //自定義 log 輸出方式
        System.out.println(message);
    }

    public Layout<ILoggingEvent> getLayout() {
        return layout;
    }

    public void setLayout(Layout<ILoggingEvent> layout) {
        this.layout = layout;
    }

    //日期格式化
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public static String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }
}
