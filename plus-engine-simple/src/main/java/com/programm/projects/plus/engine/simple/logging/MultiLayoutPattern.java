package com.programm.projects.plus.engine.simple.logging;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.Layout;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * COPIED FROM STACKOVERFLOW
 */
public class MultiLayoutPattern extends PatternLayoutEncoder {
    private List<Rule> rules = new ArrayList<>();
    private Map<String, Layout<ILoggingEvent>> layoutMap = new HashMap<>();

    public void addRule(Rule rule) {
        this.rules.add(rule);
        rule.start(context);
    }

    public byte[] encode(ILoggingEvent event) {
        Layout<ILoggingEvent> layout = getLayout(event.getLoggerName());
        String txt = layout.doLayout(event);
        return convertToBytes(txt);
    }

    private byte[] convertToBytes(String s) {
        Charset charset = getCharset();
        if (charset == null) {
            return s.getBytes();
        } else {
            return s.getBytes(charset);
        }
    }

    private Layout<ILoggingEvent> getLayout(final String name) {

        if (name == null) {
            throw new IllegalArgumentException("name argument cannot be null");
        }

        // if we are asking for the root logger, then let us return it without
        // wasting time
        if (Logger.ROOT_LOGGER_NAME.equalsIgnoreCase(name)) {
            return this.getLayout();
        }

        if (layoutMap.containsKey(name)) {
            return layoutMap.get(name);
        }

        // split package and class names
        String[] nameParts = name.split("[.]");

        // get root layout
        Layout<ILoggingEvent> layout = getLayout();

        // it is used for best match rule
        int bestMatch = 0;
        for (Rule rule : rules) {
            String[] loggerParts = rule.getLoggerParts();
            int i = 0;
            // match parts
            while (i < loggerParts.length && i < nameParts.length && nameParts[i].equals(loggerParts[i])) {
                i++;
            }

            if (i == loggerParts.length && i > bestMatch) {
                bestMatch = i;
                layout = rule.getPatternLayoutEncoder().getLayout();
            }
        }
        // save layout with logger
        layoutMap.put(name, layout);

        return layout;
    }

    @Override
    public void start() {
        super.start();
    }

    public static class Rule {
        private String logger;
        private String[] loggerParts;
        private String pattern;
        private PatternLayoutEncoder patternLayoutEncoder;
        private boolean outputPatternAsHeader = false;

        public String getLogger() {
            return logger;
        }

        public void setLogger(String logger) {
            this.logger = logger;
            this.loggerParts = logger.split("[.]");
        }

        public String[] getLoggerParts() {
            return loggerParts;
        }

        public String getPattern() {
            return pattern;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }

        public boolean isOutputPatternAsHeader() {
            return outputPatternAsHeader;
        }

        public void setOutputPatternAsHeader(boolean outputPatternAsHeader) {
            this.outputPatternAsHeader = outputPatternAsHeader;
        }

        public PatternLayoutEncoder getPatternLayoutEncoder() {
            return patternLayoutEncoder;
        }

        public void start(Context context) {
            patternLayoutEncoder = new PatternLayoutEncoder();
            patternLayoutEncoder.setPattern(pattern);
            patternLayoutEncoder.setContext(context);
            patternLayoutEncoder.setOutputPatternAsHeader(outputPatternAsHeader);
            patternLayoutEncoder.start();
        }
    }
}