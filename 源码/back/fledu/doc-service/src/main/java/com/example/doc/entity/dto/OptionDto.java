package com.example.doc.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class OptionDto {
    String type;
    List<Integer> options;
}
