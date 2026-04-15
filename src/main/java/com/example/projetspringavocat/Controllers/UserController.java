package com.example.projetspringavocat.Controllers;

import com.example.projetspringavocat.Dtos.ApiResponse;
import com.example.projetspringavocat.Dtos.ClientMapper;
import com.example.projetspringavocat.Dtos.ClientRequest;
import com.example.projetspringavocat.Dtos.ClientResponse;
import com.example.projetspringavocat.Dtos.DashboardStatsDTO;
import com.example.projetspringavocat.Entities.User;
import com.example.projetspringavocat.Services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/secretaire/{id}")
    public ResponseEntity<ClientResponse> getClientById(@PathVariable Integer id) {
        User user = userService.getClientById(id);
        ClientResponse response = ClientMapper.toResponse(user);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/secretaire/all")
    public ResponseEntity<Page<ClientResponse>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
        Page<User> usersPage = userService.getAllUsers(page, size);
        Page<ClientResponse> responsePage = usersPage.map(ClientMapper::toResponse);
        return ResponseEntity.ok(responsePage);
    }
    @GetMapping("/secretaire/verif")
    public ResponseEntity<Page<ClientResponse>> getNonVerif(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
        Page<User> usersPage = userService.getUsersNonVerif(page, size);
        Page<ClientResponse> responsePage = usersPage.map(ClientMapper::toResponse);
        return ResponseEntity.ok(responsePage);
    }

    @PutMapping("/secretaire/edit/{id}")
    public ResponseEntity<ClientResponse> modifierClient(@PathVariable Integer id, @RequestBody ClientRequest clientRequest) {
        User client = ClientMapper.toUser(clientRequest);
        User user = userService.modifierClient(id, client);
        return ResponseEntity.ok(ClientMapper.toResponse(user));
    }

    @DeleteMapping("/secretaire/del/{id}")
    public ResponseEntity<ApiResponse> supprimerClient(@PathVariable Integer id) {
        userService.supprimerClient(id);
        return ResponseEntity.ok(new ApiResponse(true, "User deleted successfully",null));
    }

    @PutMapping("/secretaire/verify/{id}")
    public ResponseEntity<ClientResponse> verifyClient(@PathVariable Integer id) {
        User user = userService.verifierClient(id);
        ClientResponse response = ClientMapper.toResponse(user);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/me")
    public ResponseEntity<ClientResponse> Getme(){
        return ResponseEntity.ok(ClientMapper.toResponse(userService.getCurrentUserEntity()));

    }

    @GetMapping("/secretaire/dashboard")
    public ResponseEntity<DashboardStatsDTO> getDashboard() {
        return ResponseEntity.ok(userService.getDashboardStats());
    }

}
