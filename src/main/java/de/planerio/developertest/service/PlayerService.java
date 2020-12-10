package de.planerio.developertest.service;

import de.planerio.developertest.controller.commons.exceptions.ResourceNotFoundException;
import de.planerio.developertest.controller.commons.player.PlayerPosition;
import de.planerio.developertest.controller.commons.player.PlayerRequest;
import de.planerio.developertest.controller.commons.player.PlayerResponse;
import de.planerio.developertest.model.entities.Player;
import de.planerio.developertest.model.repository.PlayerRepository;
import de.planerio.developertest.service.converters.PlayerConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

  private PlayerRepository playerRepository;
  private PlayerConverter playerConverter;

  @Autowired
  public PlayerService(PlayerRepository playerRepository, PlayerConverter playerConverter) {
    this.playerRepository = playerRepository;
    this.playerConverter = playerConverter;
  }

  public PlayerResponse createPlayer(PlayerRequest playerRequest) {
    Player player =
        Player.builder()
            .name(playerRequest.getName())
            .position(playerRequest.getPosition())
            .shirtNumber(playerRequest.getShirtNumber())
            .teamId(playerRequest.getTeamId())
            .build();
    return playerConverter.map(playerRepository.save(player), PlayerResponse.class);
  }

  public PlayerResponse updatePlayer(Long id, PlayerRequest playerRequest) {
    playerRepository
        .findById(id)
        .orElseThrow(
            () -> new ResourceNotFoundException(Player.class.getSimpleName(), String.valueOf(id)));

    Player player =
        Player.builder()
            .id(id)
            .name(playerRequest.getName())
            .position(playerRequest.getPosition())
            .shirtNumber(playerRequest.getShirtNumber())
            .teamId(playerRequest.getTeamId())
            .build();
    return playerConverter.map(playerRepository.save(player), PlayerResponse.class);
  }

  public PlayerResponse deletePlayer(Long id) {
    Optional<Player> playerOptional = playerRepository.findById(id);
    if (playerOptional.isPresent()) {
      PlayerResponse playerResponse =
          playerConverter.map(playerOptional.get(), PlayerResponse.class);
      playerRepository.deleteById(id);
      return playerResponse;
    }
    throw new ResourceNotFoundException(Player.class.getSimpleName(), String.valueOf(id));
  }

  public PlayerResponse getPlayerById(Long id) {
    Optional<Player> playerOptional = playerRepository.findById(id);
    if (playerOptional.isPresent()) {
      return playerConverter.map(playerOptional.get(), PlayerResponse.class);
    }
    throw new ResourceNotFoundException(Player.class.getSimpleName(), String.valueOf(id));
  }

  public List<PlayerResponse> getPlayers() {
    List<PlayerResponse> playerResponseList = new ArrayList<>();
    playerRepository
        .findAll()
        .forEach(
            player -> playerResponseList.add(playerConverter.map(player, PlayerResponse.class)));
    return playerResponseList;
  }

  public List<PlayerResponse> getPlayersByPosition(String position) {
    List<Player> playersEntitties = playerRepository.findByPosition(position);
    List<PlayerResponse> playerResponseList = new ArrayList<>();
    playerConverter.mapAsCollection(playersEntitties, playerResponseList, PlayerResponse.class);
    return playerResponseList;
  }

  public List<PlayerResponse> getDefenderPlayers() {
    List<Player> playersEntitties =
        playerRepository.findByPositionIn(PlayerPosition.getDefensivePositions());
    List<PlayerResponse> playerResponseList = new ArrayList<>();
    playerConverter.mapAsCollection(playersEntitties, playerResponseList, PlayerResponse.class);
    return playerResponseList;
  }

  public Boolean existsPlayerWithShirtNumberAndId(Integer shirtNumber, Long teamId) {
    return playerRepository.findByShirtNumberAndTeamId(shirtNumber, teamId).isPresent()
        ? Boolean.TRUE
        : Boolean.FALSE;
  }

  public Integer numberOfPlayersByTeam(Long teamId) {
    return playerRepository.countPlayersByTeamId(teamId);
  }

  public Boolean existsPlayerById(Long playerId) {
    Optional<Boolean> playerExists = playerRepository.existsPlayerById(playerId);
    return playerExists.isPresent() ? playerExists.get() : Boolean.FALSE;
  }
}
