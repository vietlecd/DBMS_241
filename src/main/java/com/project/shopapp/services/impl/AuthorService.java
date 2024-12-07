package com.project.shopapp.services.impl;

import com.project.shopapp.DTO.AuthorDTO;
import com.project.shopapp.helpers.AuthorHelper;
import com.project.shopapp.models.Author;
import com.project.shopapp.customexceptions.DataNotFoundException;
import com.project.shopapp.customexceptions.InvalidParamException;
import com.project.shopapp.models.Role;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.AuthorRepository;
import com.project.shopapp.repositories.AuthorRepositoryCustom;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.responses.AuthorResponse;
import com.project.shopapp.services.IAuthorService;
import com.project.shopapp.services.INotificationService;
import com.project.shopapp.utils.CheckExistedUtils;
import com.project.shopapp.utils.NotificationUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class AuthorService implements IAuthorService {
    private UserRepository userRepository;
    private AuthorRepository authorRepository;
    private AuthorRepositoryCustom authorRepositoryCustom;
    private AuthorHelper authorHelper;
    private CheckExistedUtils checkExistedUtils;
    private INotificationService notificationService;
    private NotificationUtils notificationUtils;

    @Override
    public AuthorResponse infoAuthor(User user) {
        Optional<Author> author1 = authorRepository.findAuthorByUser_Username(user.getUsername());
        if (author1.isPresent()) {
            Author author = author1.get();
            AuthorResponse res = AuthorResponse.builder()
                    .id_card(author.getIdCard())
                    .bio(author.getBio())
                    .fullname(author.getUser().getFullName())
                    .username(author.getUser().getUsername())
                    .phone_number(author.getUser().getPhoneNumber())
                    .build();
            return res;
        } else {
            throw new DataNotFoundException("Author not found");
        }
    }

    @Override
    public void becomeAuthor(User user, AuthorDTO authorDTO) throws InvalidParamException {
        if (!authorRepository.existsAuthorByUser(user)) {
            Author author = Author.builder()
                    .user(user)
                    .bio(authorDTO.getBio())
                    .idCard(authorDTO.getId_card())
                    .status(0)
                    .build();
            authorRepository.save(author);
        }
        else
           throw new InvalidParamException("Author has been existed");
    }
    @Override
    public ResponseEntity<String> acceptedAuthor(String username) {
        Optional<User> userOptional= userRepository.findByUsername(username);
        Optional<Author> author = authorHelper.getAuthorByUsernameAndStatus(username, 0);
        if (author.isPresent() && userOptional.isPresent()) {
            Author author1 = author.get();
            User user = userOptional.get();
            author1.setStatus(1);

            String message = notificationUtils.return_author("accept");
            notificationService.createNotification(message, user);
            authorRepository.save(author1);
        } else {
            throw new DataNotFoundException("Author not found" + username);
        }

        return new ResponseEntity<>("Add author successfully", HttpStatus.ACCEPTED);
    }
//
    @Override
    public ResponseEntity<String> deniedAuthor(String username) {
        Optional<User> userOptional= userRepository.findByUsername(username);
        Optional<Author> author = authorHelper.getAuthorByUsernameAndStatus(username, 0);
        if (author.isPresent() && userOptional.isPresent()) {
            Author author1 = author.get();
            User user = userOptional.get();

            String message = notificationUtils.return_author("denied");
            notificationService.createNotification(message, user);
            author1.setStatus(-1);
            authorRepository.save(author1);

        } else {
            throw new DataNotFoundException("Author not found" + username);
        }

        return new ResponseEntity<>("Deny author successfully", HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<String> deleteAuthor(String username) {
        Optional<Author> author = authorHelper.getAuthorByUsernameAndStatus(username, 1);
        if (author.isEmpty()) {
            throw new DataNotFoundException("Author not found" + username);
        }
        Author author1 = author.get();
        Role authorRole = new Role();
        authorRole.setId(2);

        User user1 = author1.getUser();
        user1.setRole(authorRole);

        userRepository.save(user1);

        authorRepository.delete(author1);

        return new ResponseEntity<>("Delete author successfully", HttpStatus.ACCEPTED);
    }

    @Override
    public List<AuthorResponse> getAuThor() {
        try {
            return authorRepositoryCustom.getAuthorsByStatus(1);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Author> getAuthorByName(String authorName) {
        List<Author> authors = authorRepository.findAuthorByUser_FullName(authorName);

        checkExistedUtils.checkObjectExisted(authors, "author");
        return authors;
    }

    @Override
    public List<AuthorResponse> getAuthorRequest() {
        try {
            return authorRepositoryCustom.getAuthorsByStatus(0);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }


}
