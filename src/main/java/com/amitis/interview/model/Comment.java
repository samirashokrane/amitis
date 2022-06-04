package com.amitis.interview.model;


import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@SuperBuilder(toBuilder = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class Comment extends BaseEntity {

    @Column(name = "post_id")
    Integer postId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "post_id", referencedColumnName = "id", insertable = false, updatable = false,foreignKey = @ForeignKey(name="FK_N_COMMENT_1_POST"))
    Post post;



    @Column(name = "name")
    String name;

    @Column(name = "email")
    String email;

    @Column(name = "body" , length = 10000)
    String body;
}
