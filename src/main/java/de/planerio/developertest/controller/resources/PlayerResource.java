package de.planerio.developertest.controller.resources;

import de.planerio.developertest.controller.commons.player.PlayerLastNameSortingOrder;
import de.planerio.developertest.controller.commons.player.PlayerRequest;
import de.planerio.developertest.controller.commons.player.PlayerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.websocket.server.PathParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Api("Resources for Player Manipulation")
@RequestMapping(value = PlayerResource.RESOURCE_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public interface PlayerResource {

  String RESOURCE_PATH = "/api/player";

  @ApiOperation("Create a new Player")
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  PlayerResponse createPlayer(
      @ApiParam(value = "Data of the Player to create", required = true) @RequestBody
          PlayerRequest playerRequest);

  @ApiOperation("Update an existing Player")
  @PutMapping("/{id}")
  PlayerResponse updatePlayer(
      @ApiParam(value = "Id of the Player to update", required = true) @PathVariable("id") Long id,
      @ApiParam(value = "Data of the Player to update", required = true) @RequestBody
          PlayerRequest playerRequest);

  @ApiOperation("Delete an existing Player")
  @DeleteMapping("/{id}")
  PlayerResponse deletePlayer(
      @ApiParam(value = "Id of the Player to delete", required = true) @PathParam("id") Long id);

  @ApiOperation("Return an Player given an id")
  @GetMapping("/{id}")
  PlayerResponse getPlayerById(
      @ApiParam(value = "Id of the Player to search", required = true) @PathVariable("id") Long id);

  @ApiOperation("Return all Players on the DB")
  @GetMapping("/")
  List<PlayerResponse> getPlayers();

  @ApiOperation("Return all Players on the DB")
  @GetMapping("/position/{position}")
  List<PlayerResponse> getPlayersPerPosition(
      @ApiParam(value = "Position to search for players", required = true) @PathVariable("position")
          String position);

  @ApiOperation("Return all Defensive Players on the DB")
  @GetMapping("/position/defenders")
  List<PlayerResponse> getDefenderPlayers(
      @RequestParam(name = "order", defaultValue = "ASC") PlayerLastNameSortingOrder order);
}
