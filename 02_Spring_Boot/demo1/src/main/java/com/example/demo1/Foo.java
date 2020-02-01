package com.example.demo1;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Foo {
    private Integer id;
    private String bar;
}
