package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Schema(
        description = "Post Model Information"
)
public class PostDto {
    private Long Id;

    @Schema(
            description = "Post title"
    )
    @NotEmpty
    @Size(min=2, message = "Post title should have atleast 2 characters")
    private String title;

    @Schema(
            description = "Post Description"
    )
    @NotEmpty
    @Size(min=10, message = "Post description should have atleast 10 characters")
    private String description;

    @Schema(
            description = "Post content"
    )
    @NotEmpty
    private String content;
    private Set<CommentDto> comments;

    @Schema(
            description = "Post category"
    )
    private Long categoryId;
}
