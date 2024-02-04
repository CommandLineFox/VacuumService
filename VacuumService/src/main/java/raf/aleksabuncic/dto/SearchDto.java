package raf.aleksabuncic.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class SearchDto {
    String name;
    List<Integer> status;
    Long dateFrom;
    Long dateTo;
}
