package org.jojoldu.book.springbootaws.web.dto;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
//@AllArgsConstructor
public class HelloResponseDto {

    private final String name;
    private final int amount;

}
