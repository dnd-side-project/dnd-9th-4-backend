package com.dnd.health.domain.post.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Content {

    @Column(name = "content")
    private String value;

    protected Content() {
    }

    private Content(String value) {
        this.value = value;
    }

    public static Content from(final String content) {
        return new Content(content);
    }

    public String to(){
        return this.value;
    }
}
