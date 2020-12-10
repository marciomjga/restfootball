package de.planerio.developertest.controller.resources;

import de.planerio.developertest.controller.commons.league.LeagueRequest;
import de.planerio.developertest.controller.commons.league.LeagueResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Api("Resources for League Manipulation")
@RequestMapping(path = LeagueResource.RESOURCE_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public interface LeagueResource {

  String RESOURCE_PATH = "/api/league";

  @ApiOperation("Create a new League")
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  LeagueResponse createLeague(
      @ApiParam(value = "League data from the object to be created", required = true) @RequestBody
          LeagueRequest leagueRequest);

  @ApiOperation("Update an existing League")
  @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  LeagueResponse updateLeague(
      @ApiParam(value = "Id of the League to be updated", required = true) @PathVariable("id") Long id,
      @ApiParam(value = "League data from the object to be updated", required = true) @RequestBody
          LeagueRequest leagueRequest);

  @ApiOperation("Delete an existing League")
  @DeleteMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  LeagueResponse deleteLeague(
      @ApiParam(value = "Id of the League to be deleted", required = true) @PathVariable("id")
          Long id);

  @ApiOperation("Return an League given an id")
  @GetMapping(path = "/{id}")
  LeagueResponse getLeagueById(
      @ApiParam(value = "Id of the league to be retrieved", required = true) @PathVariable("id")
          Long id);

  @ApiOperation("Return all Leagues on the DB")
  @GetMapping
  List<LeagueResponse> getLeagues();

  @ApiOperation("Return all Leagues on the DB")
  @GetMapping(path = "/language/{language}")
  List<LeagueResponse> getLeaguesPerLanguage(
      @ApiParam(value = "Language to search for Leagues", required = true) @PathVariable("language")
          String language);
}
