package org.training360.finalexam.teams;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.training360.finalexam.players.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;


    public List<TeamDTO> listTeams() {
        return teamRepository.findAll().stream()
                .map(team -> modelMapper.map(team, TeamDTO.class))
                .collect(Collectors.toList());
    }

    public TeamDTO createTeam(CreateTeamCommand command) {
        Team team = new Team(
                command.getName());
        teamRepository.save(team);
        return modelMapper.map(team, TeamDTO.class);
    }

    @Transactional
    public TeamDTO addPlayer(CreatePlayerCommand command, long id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException());
        Player player = new Player(
                command.getName(),
                command.getBirth(),
                command.getPosition());
        team.addPlayer(player);
        return modelMapper.map(team, TeamDTO.class);
    }

    @Transactional
    public TeamDTO updatePlayer(UpdateWithExistingPlayerCommand command, long id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException());
        Player player = playerRepository.findById(command.getId()).orElseThrow(() -> new PlayerNotFoundException());
        int position = team.getPlayers().stream()
                .filter(p -> p.getPosition() == player.getPosition())
                .collect(Collectors.toList()).size();
        if (player.getTeam() == null && position < 2) {
            team.addPlayer(player);
        }
        return modelMapper.map(team, TeamDTO.class);
    }
}
