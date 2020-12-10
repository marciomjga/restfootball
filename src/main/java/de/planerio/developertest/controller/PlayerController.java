package de.planerio.developertest.controller;

import de.planerio.developertest.controller.commons.exceptions.InvalidInputException;
import de.planerio.developertest.controller.commons.exceptions.ResourceNotFoundException;
import de.planerio.developertest.controller.commons.player.PlayerLastNameSortingOrder;
import de.planerio.developertest.controller.commons.player.PlayerPosition;
import de.planerio.developertest.controller.commons.player.PlayerRequest;
import de.planerio.developertest.controller.commons.player.PlayerResponse;
import de.planerio.developertest.controller.resources.PlayerResource;
import de.planerio.developertest.service.PlayerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController implements PlayerResource {

  private static final Integer MAX_PLAYERS_PER_TEAM = 25;
  private PlayerService playerService;

  @Autowired
  public PlayerController(PlayerService playerService) {
    this.playerService = playerService;
  }

  @Override
  public PlayerResponse createPlayer(PlayerRequest playerRequest) {
    validateMaxPlayersByTeam(playerRequest.getTeamId());
    validatePlayerWithShirtAndNumber(playerRequest.getShirtNumber(), playerRequest.getTeamId());
    return playerService.createPlayer(playerRequest);
  }

  @Override
  public PlayerResponse updatePlayer(Long id, PlayerRequest playerRequest) {
    validateMaxPlayersByTeam(playerRequest.getTeamId());
    validatePlayerWithShirtAndNumber(playerRequest.getShirtNumber(), playerRequest.getTeamId());
    return playerService.updatePlayer(id, playerRequest);
  }

  @Override
  public PlayerResponse deletePlayer(Long id) {
    validatePlayerExists(id);
    return playerService.deletePlayer(id);
  }

  @Override
  public PlayerResponse getPlayerById(Long id) {
    return playerService.getPlayerById(id);
  }

  @Override
  public List<PlayerResponse> getPlayers() {
    return playerService.getPlayers();
  }

  @Override
  public List<PlayerResponse> getPlayersPerPosition(String position) {
    validatePlayerPosition(position);
    return playerService.getPlayersByPosition(position);
  }

  @Override
  public List<PlayerResponse> getDefenderPlayers(PlayerLastNameSortingOrder order) {
    return playerService.getDefenderPlayers();
  }

  private void validatePlayerPosition(String position) {
    PlayerPosition playerPosition = PlayerPosition.valueOf(position);
    if (playerPosition == null) {
      throw new InvalidInputException(String.format("Player position %s doesn't exists", position));
    }
  }

  private void validatePlayerExists(Long id) {
    Boolean playerExists = playerService.existsPlayerById(id);
    if (!playerExists) {
      throw new ResourceNotFoundException("Player", id.toString());
    }
  }

  private void validateMaxPlayersByTeam(Long teamId) {
    Integer countPlayersOnTeam = playerService.numberOfPlayersByTeam(teamId);
    if (countPlayersOnTeam == MAX_PLAYERS_PER_TEAM) {
      throw new InvalidInputException(
          String.format("Team with id %s has reached the max number of players.", teamId));
    }
  }

  private void validatePlayerWithShirtAndNumber(Integer shirtNumber, Long teamId) {
    Boolean exitsPlayer = playerService.existsPlayerWithShirtNumberAndId(shirtNumber, teamId);
    if (exitsPlayer) {
      throw new InvalidInputException(
          String.format(
              "Player with %s shirt number and %s team id already exists", shirtNumber, teamId));
    }
  }

}
