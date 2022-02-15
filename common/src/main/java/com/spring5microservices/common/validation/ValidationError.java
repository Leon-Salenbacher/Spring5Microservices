package com.spring5microservices.common.validation;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

import static java.util.Optional.ofNullable;

@Getter
@EqualsAndHashCode
public class ValidationError implements Comparable<ValidationError> {

    private int priority;          // Greater means more priority
    private String errorMessage;


    /**
     * Construct an {@code ValidationError}.
     *
     * @param priority
     *    The importance of the current error
     * @param errorMessage
     *    Error message
     */
    private ValidationError(int priority, String errorMessage) {
        this.priority = priority;
        this.errorMessage = errorMessage;
    }


    /**
     * Returns an {@code ValidationError} adding the given {@code priority} and {@code errorMessage}.
     *
     * @param priority
     *    The importance of the current error
     * @param errorMessage
     *    Error message
     *
     * @return {@code ValidationError}
     *
     * @throws NullPointerException if {@code errorMessage} is {@code null}
     */
    public static ValidationError of(int priority, String errorMessage) {
        return new ValidationError(priority, Objects.requireNonNull(errorMessage));
    }


    @Override
    public int compareTo(ValidationError other) {
        return ofNullable(other)
                .map(o -> priority - o.priority)
                .orElse(1);
    }

}