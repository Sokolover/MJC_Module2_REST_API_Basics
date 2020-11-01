package com.epam.esm.sokolov.model;

import com.epam.esm.sokolov.core.IdentifiedRow;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class GiftCertificate implements IdentifiedRow {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private LocalDateTime createDate;
    private String createDateTimeZone;
    private LocalDateTime lastUpdateDate;
    private String lastUpdateDateTimeZone;
    private Integer duration;

    private List<Tag> tags;

    public GiftCertificate() {
    }

    public GiftCertificate(String name, String description, BigDecimal price, LocalDateTime createDate, String createDateTimeZone, LocalDateTime lastUpdateDate, String lastUpdateDateTimeZone, Integer duration, List<Tag> tags) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.createDate = createDate;
        this.createDateTimeZone = createDateTimeZone;
        this.lastUpdateDate = lastUpdateDate;
        this.lastUpdateDateTimeZone = lastUpdateDateTimeZone;
        this.duration = duration;
        this.tags = tags;
    }

    public GiftCertificate(Long id, String name, String description, BigDecimal price, LocalDateTime createDate, String createDateTimeZone, LocalDateTime lastUpdateDate, String lastUpdateDateTimeZone, Integer duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.createDate = createDate;
        this.createDateTimeZone = createDateTimeZone;
        this.lastUpdateDate = lastUpdateDate;
        this.lastUpdateDateTimeZone = lastUpdateDateTimeZone;
        this.duration = duration;
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getCreateDateTimeZone() {
        return createDateTimeZone;
    }

    public void setCreateDateTimeZone(String createDateTimeZone) {
        this.createDateTimeZone = createDateTimeZone;
    }

    public String getLastUpdateDateTimeZone() {
        return lastUpdateDateTimeZone;
    }

    public void setLastUpdateDateTimeZone(String lastUpdateDateTimeZone) {
        this.lastUpdateDateTimeZone = lastUpdateDateTimeZone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftCertificate that = (GiftCertificate) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(price, that.price) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(lastUpdateDate, that.lastUpdateDate) &&
                Objects.equals(duration, that.duration) &&
                Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, createDate, lastUpdateDate, duration, tags);
    }

    @Override
    public String toString() {
        return "GiftCertificate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", duration=" + duration +
                ", tags=" + tags +
                '}';
    }
}
