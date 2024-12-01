package com.project.shopapp.controllers;

import com.project.shopapp.DTO.AuthorDTO;
import com.project.shopapp.helpers.AuthenticationHelper;
import com.project.shopapp.models.User;
import com.project.shopapp.responses.BaseProjection;
import com.project.shopapp.services.IAuthorService;
import lombok.AllArgsConstructor;
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
        User user = authenticationHelper.getUser(authentication);
        return authorService.infoAuthor(user);
    }

    @PostMapping("/become")
    public ResponseEntity<String> becomeAuthor(@RequestBody AuthorDTO authorDTO, Authentication authentication) {
        User user = authenticationHelper.getUser(authentication);
        return authorService.becomeAuthor(user, authorDTO);
    }

    @PostMapping("/accept")
    public ResponseEntity<String> acceptedAuthor(@RequestParam String username) {
        return authorService.acceptedAuthor(username);
    }

    @PostMapping("/deny")
    public ResponseEntity<String> deniedAuthor(@RequestParam String username) {
        return authorService.deniedAuthor(username);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAuthor(@RequestParam String username) {
        return authorService.deleteAuthor(username);
    }

    @GetMapping("/getAuthor")
    public List<BaseProjection> getAuthorList() {
        return authorService.getAuThor();
    }

    @GetMapping("/getAuthorRequest")
    public List<BaseProjection> getAuthorRequest() {
        return authorService.getAuthorRequest();
    }

}
