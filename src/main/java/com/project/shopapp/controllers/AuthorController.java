package com.project.shopapp.controllers;

import com.project.shopapp.DTO.AuthorDTO;
import com.project.shopapp.customexceptions.DataNotFoundException;
import com.project.shopapp.customexceptions.InvalidParamException;
import com.project.shopapp.helpers.AuthenticationHelper;
import com.project.shopapp.models.User;
import com.project.shopapp.responses.AuthorResponse;
import com.project.shopapp.services.IAuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/author")
@AllArgsConstructor
public class AuthorController {
    private IAuthorService authorService;
    private AuthenticationHelper authenticationHelper;

    @GetMapping("/info")
    public ResponseEntity<?> infoAuthor(Authentication authentication) {
        try {
            User user = authenticationHelper.getUser(authentication);
            AuthorResponse author = authorService.infoAuthor(user);
            return ResponseEntity.ok(author);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @PostMapping("/become")
    public ResponseEntity<?> becomeAuthor(@RequestBody AuthorDTO authorDTO, Authentication authentication) {
        try {
            User user = authenticationHelper.getUser(authentication);
            authorService.becomeAuthor(user, authorDTO);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
        } catch (InvalidParamException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/accept")
    public ResponseEntity<String> acceptedAuthor(@RequestParam String username) {
        try {
            return authorService.acceptedAuthor(username);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }
//
    @PostMapping("/deny")
    public ResponseEntity<String> deniedAuthor(@RequestParam String username) {
        return authorService.deniedAuthor(username);
    }
//
//    @DeleteMapping("/delete")
//    public ResponseEntity<String> deleteAuthor(@RequestParam String username) {
//        return authorService.deleteAuthor(username);
//    }
//
    @GetMapping("/getAuthor")
    public List<AuthorResponse> getAuthorList() {
        return authorService.getAuThor();
    }

    @GetMapping("/getAuthorRequest")
    public List<AuthorResponse> getAuthorRequest() {
        return authorService.getAuthorRequest();
    }

    @GetMapping("/countFollowers")
    public ResponseEntity<?> countFollower(Authentication authentication) {
        try {
            User user = authenticationHelper.getUser(authentication);
            return ResponseEntity.ok(authorService.countFollowers(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/countRecommendBook")
    public ResponseEntity<?> countRecommendBook(Authentication authentication) {
        try {
            User user = authenticationHelper.getUser(authentication);
            return ResponseEntity.ok(authorService.countRecommendBookWritten(user));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
