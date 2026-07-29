package com.company.librarysystem.controller;

import com.company.librarysystem.entity.Book;
import com.company.librarysystem.entity.IssueRecord;
import com.company.librarysystem.entity.Role;
import com.company.librarysystem.entity.User;
import com.company.librarysystem.repository.BookRepository;
import com.company.librarysystem.repository.IssueRecordRepository;
import com.company.librarysystem.repository.RoleRepository;
import com.company.librarysystem.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Controller
public class LibraryController {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final IssueRecordRepository issueRecordRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public LibraryController(BookRepository bookRepository, UserRepository userRepository,
                             IssueRecordRepository issueRecordRepository,
                             RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.issueRecordRepository = issueRecordRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        Role userRole = roleRepository.findAll().stream()
                .filter(r -> r.getName().equals("ROLE_USER"))
                .findFirst().orElseThrow();

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(userRole));

        userRepository.save(user);
        return "redirect:/login";
    }

    // --- UPDATED: Now accepts a search keyword ---
    @GetMapping("/books")
    public String viewBooks(Model model, HttpServletRequest request,
                            @RequestParam(name = "keyword", required = false) String keyword) {
        List<Book> books;

        if (keyword != null && !keyword.isEmpty()) {
            // If the user typed something, use our new custom search method!
            books = bookRepository.findByTitleContainingIgnoreCaseOrIsbnContainingIgnoreCase(keyword, keyword);
        } else {
            // Otherwise, just show all the books
            books = bookRepository.findAll();
        }

        model.addAttribute("books", books);
        model.addAttribute("keyword", keyword); // Sends the typed word back to the HTML so the search box doesn't empty out
        model.addAttribute("isAdmin", request.isUserInRole("ROLE_ADMIN"));
        return "books";
    }

    @GetMapping("/books/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-book";
    }

    @PostMapping("/books/add")
    public String saveBook(@ModelAttribute("book") Book book) {
        bookRepository.save(book);
        return "redirect:/books";
    }

    @PostMapping("/books/issue/{id}")
    public String issueBook(@PathVariable Long id, Principal principal) {
        Book book = bookRepository.findById(id).orElseThrow();
        User user = userRepository.findByUsername(principal.getName());

        if (book.getQuantity() > 0) {
            book.setQuantity(book.getQuantity() - 1);
            bookRepository.save(book);

            IssueRecord record = new IssueRecord();
            record.setBook(book);
            record.setUser(user);
            record.setIssueDate(LocalDate.now());
            record.setReturnDate(LocalDate.now().plusDays(14));
            issueRecordRepository.save(record);
        }
        return "redirect:/books";
    }

    @GetMapping("/my-books")
    public String myBooks(Model model, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        List<IssueRecord> records = issueRecordRepository.findByUser(user);
        model.addAttribute("records", records);
        return "my-books";
    }

    @PostMapping("/books/return/{id}")
    public String returnBook(@PathVariable Long id) {
        IssueRecord record = issueRecordRepository.findById(id).orElseThrow();
        Book book = record.getBook();

        book.setQuantity(book.getQuantity() + 1);
        bookRepository.save(book);
        issueRecordRepository.delete(record);

        return "redirect:/my-books";
    }
}