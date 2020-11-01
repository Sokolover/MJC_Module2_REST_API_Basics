package com.epam.esm.sokolov.dto;

import com.epam.esm.sokolov.converter.CustomDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public class GiftCertificateDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private ZonedDateTime createDate;
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private ZonedDateTime lastUpdateDate;
    private Integer duration;

    private List<TagDTO> tags;

    public GiftCertificateDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(ZonedDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }
}
