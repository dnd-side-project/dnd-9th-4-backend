package com.dnd.health.domain.post.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PostRegion {

    @Column(name = "post_region")
    private String value;

    protected PostRegion() {
    }

    private PostRegion(String value) {
        this.value = value;
    }

    public static PostRegion from(final String postRegion) {
        return new PostRegion(postRegion);
    }

    public String to(){
        return this.value;
    }
}
