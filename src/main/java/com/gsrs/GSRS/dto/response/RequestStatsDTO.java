package com.gsrs.GSRS.dto.response;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RequestStatsDTO {

    private long total;
    private long submitted;
    private long inProgress;
    private long resolved;
    private long rejected;
}
