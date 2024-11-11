package com.project.shopapp.controllers;

import com.project.shopapp.DTO.AuthorDTO;
import com.project.shopapp.helpers.AuthenticationHelper;
import com.project.shopapp.services.IAuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/author")
@AllArgsConstructor
public class AuthorController {
    private IAuthorService authorService;
    private AuthenticationHelper authenticationHelper;

    @PostMapping("/add")
    public ResponseEntity<String> becomeAuthor(@RequestBody AuthorDTO authorDTO, Authentication authentication) {
        String username = authenticationHelper.getUsername(authentication);
        return authorService.becomeAuthor(username, authorDTO);
    }

    @PostMapping("/accepted/{username}")
    public ResponseEntity<String> acceptedAuthor(@PathVariable String username) {
        return authorService.acceptedAuthor(username);
    }

    @PostMapping("/denied/{username}")
    public ResponseEntity<String> deniedAuthor(@PathVariable String username) {
        return authorService.deniedAuthor(username);
    }
}
