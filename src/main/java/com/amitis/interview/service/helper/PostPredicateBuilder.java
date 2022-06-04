package com.amitis.interview.service.helper;

import com.amitis.interview.dto.request.PostFilterRequest;
import com.amitis.interview.model.QPost;
import com.amitis.interview.model.expressionbuilder.OptionalBooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

public class PostPredicateBuilder {

    public static BooleanExpression getPredicate(final PostFilterRequest request) {


        QPost qPost = QPost.post;

        BooleanExpression predicate = new OptionalBooleanBuilder(qPost.isNotNull(), true)
                .build();
        return predicate;
    }
}
