package org.training360.finalexam.teams;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.training360.finalexam.players.CreatePlayerCommand;
import org.training360.finalexam.players.PlayerDTO;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    @GetMapping
    @Operation(summary = "list of teams")
    public List<TeamDTO> listTeams() {
        return teamService.listTeams();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "creates a team")
    @ApiResponse(responseCode = "201",description = "movie created")
    public TeamDTO createPlayer(@Valid @RequestBody CreateTeamCommand command) {
        return teamService.createTeam(command);
    }

    @PostMapping("/{id}/players")
    @Operation(summary = "add player to team")
    public TeamDTO addRate(@PathVariable("id") long id, @Valid @RequestBody CreatePlayerCommand command) {
        return teamService.addPlayer(command, id);
    }

    @PutMapping("/{id}/players")
    @Operation(summary = "update team with player")
    public TeamDTO updateTeam(@PathVariable("id") long id, @Valid @RequestBody UpdateWithExistingPlayerCommand command){
        return teamService.updatePlayer(command,id);
    }
}
