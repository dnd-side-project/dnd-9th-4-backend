package com.dnd.health.domain.post.domain;


import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Title {

    @Column(name = "title")
    private String value;

    protected Title() {
    }

    private Title(String value) {
        this.value = value;
    }

    public static Title from(final String title) {
        return new Title(title);
    }

    public String to(){
        return this.value;
    }
}
