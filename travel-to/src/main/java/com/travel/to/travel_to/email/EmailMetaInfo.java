package com.travel.to.travel_to.email;

import java.util.Map;

public class EmailMetaInfo {
    private String from;
    private String to;
    private String subject;
    private String message;
    private String fromDisplayName;
    private String emailLanguage;
    private String templateLocation;
    private Map<String, Object> context;

    public String getFrom() {
        return from;
    }

    public EmailMetaInfo setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getTo() {
        return to;
    }

    public EmailMetaInfo setTo(String to) {
        this.to = to;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public EmailMetaInfo setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public EmailMetaInfo setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getFromDisplayName() {
        return fromDisplayName;
    }

    public EmailMetaInfo setFromDisplayName(String fromDisplayName) {
        this.fromDisplayName = fromDisplayName;
        return this;
    }

    public String getEmailLanguage() {
        return emailLanguage;
    }

    public EmailMetaInfo setEmailLanguage(String emailLanguage) {
        this.emailLanguage = emailLanguage;
        return this;
    }

    public String getTemplateLocation() {
        return templateLocation;
    }

    public EmailMetaInfo setTemplateLocation(String templateLocation) {
        this.templateLocation = templateLocation;
        return this;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public EmailMetaInfo setContext(Map<String, Object> context) {
        this.context = context;
        return this;
    }
}
