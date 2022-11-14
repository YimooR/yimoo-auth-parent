package com.yimoo.system.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName YimooException
 * @Description TODO
 * @date 2022/11/15 00:00
 * @Version 1.0
 * @Author hao yang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YimooException extends RuntimeException{
    private Integer code;
    private String img;
}
