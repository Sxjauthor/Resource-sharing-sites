package com.j10.exercise.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/8 17:01
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CustomerException extends RuntimeException {
    private String message;
    private Integer code;
}
