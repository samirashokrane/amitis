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
public class Todo extends BaseEntity {

    @Column(name = "user_id")
    Integer userId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false,foreignKey = @ForeignKey(name="FK_N_TODO_1_USER"))
    UserProfile user;

    @Column(name = "title")
    String title;

    @Column(name = "completed")
    boolean completed;

}
