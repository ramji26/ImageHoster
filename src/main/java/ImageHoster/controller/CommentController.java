package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    ImageService imageService;

    @Autowired
    CommentService commentService;

    /**
     * This method creates a comment and associates it to corresponding image
     * @param comment Input Comment from POST payload
     * @param id      Image id query paremeter
     * @param title   Image title query parameter
     * @param session current session object
     * @return        redirects to Image Details page for current image
     * @throws IOException
     */
    @RequestMapping(value = "/image/{id}/{title}/comments", method = RequestMethod.POST)
    public String addNewComment(@RequestParam("comment") String comment, @PathVariable("id") Integer id, @PathVariable("title") String title, HttpSession session) {

        Comment newComment = new Comment();

        newComment.setText(comment);

        User user = (User) session.getAttribute("loggeduser");
        newComment.setUser(user);

        Image image = imageService.getImage(id);

        newComment.setImage(image);

        newComment.setCreatedDate(new Date());

        commentService.addNewComment(newComment);

        return "redirect:/images/"+id+"/"+title;

    }

}
