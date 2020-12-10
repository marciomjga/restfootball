package de.planerio.developertest.controller;

import de.planerio.developertest.controller.commons.exceptions.InvalidInputException;
import de.planerio.developertest.controller.commons.exceptions.ResourceNotFoundException;
import de.planerio.developertest.controller.commons.league.LeagueRequest;
import de.planerio.developertest.controller.commons.league.LeagueResponse;
import de.planerio.developertest.controller.resources.LeagueResource;
import de.planerio.developertest.controller.validators.LanguageValidator;
import de.planerio.developertest.service.LeagueService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LeagueController implements LeagueResource {

  private final LeagueService leagueService;

  @Autowired
  public LeagueController(LeagueService leagueService) {
    this.leagueService = leagueService;
  }

  @Override
  public LeagueResponse createLeague(LeagueRequest leagueRequest) {
    validateLeagueWithCountryId(leagueRequest.getCountryId());
    return leagueService.save(leagueRequest);
  }

  @Override
  public LeagueResponse updateLeague(Long id, LeagueRequest leagueRequest) {
    validateLeagueWithNameAndCountryId(leagueRequest.getName(), leagueRequest.getCountryId());
    validateLeagueWithIdExists(id);
    return leagueService.update(leagueRequest, id);
  }

  @Override
  public LeagueResponse deleteLeague(Long id) {
    validateLeagueWithIdExists(id);
    return leagueService.delete(id);
  }

  @Override
  public LeagueResponse getLeagueById(Long id) {
    validateLeagueWithIdExists(id);
    return leagueService.findById(id);
  }

  @Override
  public List<LeagueResponse> getLeagues() {
    return leagueService.findAll();
  }

  @Override
  public List<LeagueResponse> getLeaguesPerLanguage(String language) {
    LanguageValidator.validateLanguage(language);
    return leagueService.findLeaguesByLanguage(language);
  }

  private void validateLeagueWithIdExists(Long id) {
    Boolean idExists = leagueService.existsLeagueById(id);

    if (!idExists) {
      throw new ResourceNotFoundException("League", id.toString());
    }
  }

  private void validateLeagueWithNameAndCountryId(String name, Long countryId) {
    Boolean leagueExists = leagueService.existsLeagueInCountryId(countryId);

    if (leagueExists) {
      throw new InvalidInputException(
          String.format("League with name %s and Country id %s already exists", name, countryId));
    }
  }

  private void validateLeagueWithCountryId(Long countryId) {
    Boolean leagueExists = leagueService.existsLeagueInCountryId(countryId);

    if (leagueExists) {
      throw new InvalidInputException(
          String.format("League with Country id %s already exists", countryId));
    }
  }
}
