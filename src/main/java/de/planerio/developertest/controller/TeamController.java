package de.planerio.developertest.controller;

import de.planerio.developertest.controller.commons.exceptions.InvalidInputException;
import de.planerio.developertest.controller.commons.exceptions.ResourceNotFoundException;
import de.planerio.developertest.controller.commons.team.TeamRequest;
import de.planerio.developertest.controller.commons.team.TeamResponse;
import de.planerio.developertest.controller.resources.TeamResource;
import de.planerio.developertest.service.TeamService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController implements TeamResource {

  public static final Integer MAX_TEAMS_PER_LEAGUE = 20;
  private TeamService teamService;

  @Autowired
  public TeamController(TeamService teamService) {
    this.teamService = teamService;
  }

  @Override
  public TeamResponse createTeam(TeamRequest teamRequest) {
    validateMaxTeamsPerLeague(teamRequest.getLeagueId());
    validateTeamAlreadyExists(teamRequest.getName(), teamRequest.getLeagueId());
    return teamService.createTeam(teamRequest);
  }

  @Override
  public TeamResponse updateTeam(Long id, TeamRequest teamRequest) {
    validateTeamExistsById(id);
    validateTeamAlreadyExists(teamRequest.getName(), teamRequest.getLeagueId());
    return teamService.updateTeam(id, teamRequest);
  }

  @Override
  public TeamResponse deleteTeam(Long id) {
    validateTeamExistsById(id);
    return teamService.deleteTeam(id);
  }

  @Override
  public TeamResponse getTeamById(Long id) {
    return teamService.findById(id);
  }

  @Override
  public List<TeamResponse> getTeams() {
    return teamService.findAll();
  }

  private void validateTeamExistsById(Long id){
    Boolean teamExists = teamService.existsTeamById(id);
    if (!teamExists) {
      throw new ResourceNotFoundException("Team", id.toString());
    }
  }

  private void validateMaxTeamsPerLeague(Long leagueId){
    Integer countTeamsOnLeague = teamService.countTeamsByLeague(leagueId);
    if(countTeamsOnLeague >= MAX_TEAMS_PER_LEAGUE){
      throw new InvalidInputException(
          String.format("League with id %s has reached the max number of teams.", leagueId));
    }
  }

  private void validateTeamAlreadyExists(String name, Long leagueId){
    Boolean existsTeam = teamService.existsTeamByNameAndLeagueId(name, leagueId);
    if(existsTeam){
      throw new InvalidInputException(
          String.format(
              "Team with %s name for league id %s already exists", name, leagueId));
    }
  }
}
