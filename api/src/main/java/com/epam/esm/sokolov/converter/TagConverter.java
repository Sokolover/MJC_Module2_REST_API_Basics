package com.epam.esm.sokolov.converter;

import com.epam.esm.sokolov.dto.TagDTO;
import com.epam.esm.sokolov.model.Tag;

import java.util.List;
import java.util.stream.Collectors;

public class TagConverter {

    public static List<TagDTO> convertTagsDtosFromTag(List<Tag> tags) {
        return tags.stream()
                .map(TagDTO::new)
                .collect(Collectors.toList());
    }

    public static List<Tag> convertTagsFromTagDtos(List<TagDTO> tagDTOs) {
        return tagDTOs.stream()
                .map(tagDTO -> new Tag(tagDTO.getId(), tagDTO.getName()))
                .collect(Collectors.toList());
    }
}
