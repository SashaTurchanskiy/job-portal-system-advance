package com.zosh.job.model;

import domain.SocialPlatform;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocialLink {

    private SocialPlatform platform;
    private String url;
}
