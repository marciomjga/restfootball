package de.planerio.developertest.controller.resources;

import de.planerio.developertest.controller.commons.team.TeamRequest;
import de.planerio.developertest.controller.commons.team.TeamResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@Api("Resources for Team Manipulation")
@RequestMapping("/api/team")
public interface TeamResource {
    @ApiOperation("Create a new Team")
    @PostMapping("/")
    TeamResponse createTeam(TeamRequest teamRequest);

    @ApiOperation("Update an existing Team")
    @PutMapping("/{id}")
    TeamResponse updateTeam(@PathParam("id") Long id, @RequestBody TeamRequest teamRequest);

    @ApiOperation("Delete an existing Team")
    @DeleteMapping("/{id}")
    TeamResponse deleteTeam(@PathParam ("id") Long id);

    @ApiOperation("Return an Team given an id")
    @GetMapping("/{id}")
    TeamResponse getTeamById(@PathParam ("id") Long id);

    @ApiOperation("Return all Teams on the DB")
    @GetMapping("/")
    List<TeamResponse> getTeams();

}
