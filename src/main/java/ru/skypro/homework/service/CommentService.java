package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CreateCommentDTO;
import ru.skypro.homework.dto.ResponseWrapper;
import ru.skypro.homework.entity.Comment;

/**
 * Interface CommentService
 * The interface is used to work with comments
 * Possibility to receives all comments, receives a comment, adds a comment,deletes a comment,
 * deletes everything by the ad ID, updates the comment
 *
 * @author Bogomolov Ilya
 * @author Kilikova Anna
 */
public interface CommentService {
    /**
     * Method of getting all comments
     *
     * @param id identifier ads
     * @return Outputs the response wrapper
     */
    ResponseWrapper<CommentDTO> getAllComments(int id);

    /**
     * Method outputs the addition of a comment
     *
     * @param commentId comment identification number
     * @return displays the added comment
     */
    Comment getComment(long commentId);

    /**
     * Method adds a comment
     *
     * @param id             identification number
     * @param comment        the DTO comment being created
     * @param authentication authentication
     * @return outputs a new DTO comment
     */
    CommentDTO addComment(int id, CreateCommentDTO comment, Authentication authentication);

    /**
     * Method of deleting a comment
     *
     * @param adId      identifier ads
     * @param commentId comment identification number
     */
    void deleteComment(int adId, int commentId);

    /**
     * The method deletes everything by the ad ID
     *
     * @param adId ad identification number
     */
    void deleteAllByAdId(long adId);

    /**
     * Update method comment
     *
     * @param adId       ad identification number
     * @param commentId  comment identification number
     * @param comment DTO comment
     * @return outputs the user's DTO comment
     */
    CommentDTO updateComment(int adId, int commentId, CommentDTO comment);


}
