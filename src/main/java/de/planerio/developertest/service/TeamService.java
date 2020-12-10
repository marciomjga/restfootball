package de.planerio.developertest.service;

import de.planerio.developertest.controller.commons.exceptions.ResourceNotFoundException;
import de.planerio.developertest.controller.commons.team.TeamRequest;
import de.planerio.developertest.controller.commons.team.TeamResponse;
import de.planerio.developertest.model.entities.Team;
import de.planerio.developertest.model.repository.TeamRepository;
import de.planerio.developertest.service.converters.TeamConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

  private TeamRepository teamRepository;
  private TeamConverter teamConverter;

  @Autowired
  public TeamService(TeamRepository teamRepository, TeamConverter teamConverter) {
    this.teamRepository = teamRepository;
    this.teamConverter = teamConverter;
  }

  public TeamResponse createTeam(TeamRequest teamRequest) {
    Team team =
        Team.builder().leagueId(teamRequest.getLeagueId()).name(teamRequest.getName()).build();
    return teamConverter.map(teamRepository.save(team), TeamResponse.class);
  }

  public TeamResponse updateTeam(Long id, TeamRequest teamRequest) {
    teamRepository
        .findById(id)
        .orElseThrow(
            () -> new ResourceNotFoundException(Team.class.getSimpleName(), String.valueOf(id)));
    Team team =
        Team.builder()
            .id(id)
            .leagueId(teamRequest.getLeagueId())
            .name(teamRequest.getName())
            .build();

    return teamConverter.map(teamRepository.save(team), TeamResponse.class);
  }

  public TeamResponse deleteTeam(Long id) {
    Optional<Team> teamOptional = teamRepository.findById(id);
    if (teamOptional.isPresent()) {
      TeamResponse teamResponse = teamConverter.map(teamOptional.get(), TeamResponse.class);
      teamRepository.deleteById(id);
      return teamResponse;
    }
    throw new ResourceNotFoundException(Team.class.getSimpleName(), String.valueOf(id));
  }

  public TeamResponse findById(Long id) {
    return teamConverter.map(teamRepository.findById(id), TeamResponse.class);
  }

  public List<TeamResponse> findAll() {
    List<TeamResponse> teamResponseList = new ArrayList<>();
    teamRepository
        .findAll()
        .forEach(team -> teamResponseList.add(teamConverter.map(team, TeamResponse.class)));
    return teamResponseList;
  }

  public Boolean existsTeamByNameAndLeagueId(String name, Long teamId) {
    Optional<Boolean> existsTeam = teamRepository.existsTeamByNameAndLeagueId(name, teamId);
    return existsTeam.isPresent() ? existsTeam.get() : Boolean.FALSE;
  }

  public Boolean existsTeamById(Long id) {
    Optional<Boolean> existsTeam = teamRepository.existsTeamById(id);
    return existsTeam.isPresent() ? existsTeam.get() : Boolean.FALSE;
  }

  public Integer countTeamsByLeague(Long leagueId){
    return teamRepository.countTeamsByLeagueId(leagueId);
  }
}
