package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CreateCommentDTO;
import ru.skypro.homework.dto.ResponseWrapper;
import ru.skypro.homework.service.CommentService;

/**
 * Controller CommentController
 * The controller is used for issues all comments, adds a comment,
 * deletes a comment, updates the comment by id
 *
 * @author Bogomolov Ilya
 * @see CommentService
 */
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("ads")
@Tag(name = "Comments")
public class CommentController {
    private final CommentService commentService;

    @Operation(
            summary = "Get all comments",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "All comments received",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapper.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "All comments not received",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapper.class)
                            )
                    )
            }
    )
    @GetMapping("{id}/comments")
    public ResponseEntity<ResponseWrapper<CommentDTO>> getAllComments (@PathVariable int id) {
        return ResponseEntity.ok( commentService.getAllComments(id));
    }

    @Operation(
            summary = "Add a comment",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Comment added successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Error adding comment",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentDTO.class)
                            )
                    )
            }
    )
    @PostMapping("{id}/comments")
    public ResponseEntity<CommentDTO> addComment (@PathVariable int id,
                                                  @RequestBody CreateCommentDTO comment,
                                                  Authentication authentication) {
        return ResponseEntity.ok(commentService.addComment(id, comment, authentication));
    }

    @Operation(
            summary = "Delete a comment",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Comment successfully deleted",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Error deleting comment",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentDTO.class)
                            )
                    )
            }
    )
    @PreAuthorize("@commentServiceImpl.getComment(#commentId).author.email == authentication.name or hasRole('ADMIN')")
    @DeleteMapping("{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment (@PathVariable int adId,
                                               @PathVariable int commentId) {
        commentService.deleteComment(adId, commentId);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Update comment by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Comment edited successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Error editing comment",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentDTO.class)
                            )
                    )
            }
    )
    @PreAuthorize("@commentServiceImpl.getComment(#commentId).author.email == authentication.name or hasRole('ADMIN')")
    @PatchMapping("{adId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment (@PathVariable int adId,
                                         @PathVariable int commentId,
                                                     @RequestBody CommentDTO comment) {
        return ResponseEntity.ok(commentService.updateComment(adId, commentId, comment));
    }
}
