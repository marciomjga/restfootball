package de.planerio.developertest.service;

import de.planerio.developertest.controller.commons.exceptions.ResourceNotFoundException;
import de.planerio.developertest.controller.commons.league.LeagueRequest;
import de.planerio.developertest.controller.commons.league.LeagueResponse;
import de.planerio.developertest.model.entities.League;
import de.planerio.developertest.model.repository.LeagueRepository;
import de.planerio.developertest.service.converters.LeagueConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeagueService {

  private LeagueRepository leagueRepository;
  private LeagueConverter leagueConverter;

  @Autowired
  public LeagueService(LeagueRepository leagueRepository, LeagueConverter leagueConverter) {
    this.leagueRepository = leagueRepository;
    this.leagueConverter = leagueConverter;
  }

  public LeagueResponse save(LeagueRequest leagueRequest) {
    League league =
        League.builder()
            .countryId(leagueRequest.getCountryId())
            .name(leagueRequest.getName())
            .build();

    league = leagueRepository.save(league);
    return leagueConverter.map(league, LeagueResponse.class);
  }

  public List<LeagueResponse> findAll() {
    List<LeagueResponse> allLeagues = new ArrayList<>();
    leagueRepository
        .findAll()
        .forEach(league -> allLeagues.add(leagueConverter.map(league, LeagueResponse.class)));
    return allLeagues;
  }

  public LeagueResponse findById(Long leagueId) {
    Optional<League> leagueOptional = leagueRepository.findById(leagueId);
    if (leagueOptional.isPresent()) {
      return leagueConverter.map(leagueOptional.get(), LeagueResponse.class);
    }
    throw new ResourceNotFoundException(League.class.getSimpleName(), String.valueOf(leagueId));
  }

  public LeagueResponse update(LeagueRequest leagueRequest, Long leagueId) {
    leagueRepository
        .findById(leagueId)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    League.class.getSimpleName(), String.valueOf(leagueId)));

    League league =
        League.builder()
            .id(leagueId)
            .name(leagueRequest.getName())
            .countryId(leagueRequest.getCountryId())
            .build();

    return leagueConverter.map(leagueRepository.save(league), LeagueResponse.class);
  }

  public LeagueResponse delete(Long leagueId) {
    Optional<League> optionalLeague = leagueRepository.findById(leagueId);

    if (optionalLeague.isPresent()) {
      LeagueResponse leagueResponse =
          leagueConverter.map(optionalLeague.get(), LeagueResponse.class);
      leagueRepository.deleteById(leagueId);
      return leagueResponse;
    }

    throw new ResourceNotFoundException(League.class.getSimpleName(), String.valueOf(leagueId));
  }

  public List<LeagueResponse> findLeaguesByLanguage(String language) {
    List<League> leagues = leagueRepository.findByCountryLanguage(language);

    if (Objects.nonNull(leagues) && !leagues.isEmpty()) {
      List<LeagueResponse> leagueResponseList = new ArrayList<>();
      leagues.forEach(
          league -> leagueResponseList.add(leagueConverter.map(league, LeagueResponse.class)));
      return leagueResponseList;
    }

    throw new ResourceNotFoundException(
        String.format("Could not find Leagues for language '%s'", language));
  }

  public Boolean existsLeagueById(Long leagueId) {
    return leagueRepository.existsLeagueById(leagueId).orElse(Boolean.FALSE);
  }

  public Boolean existsLeagueByNameAndCountryId(String name, Long countryId) {
    return leagueRepository.existsLeagueByNameAndCountryId(name, countryId).orElse(Boolean.FALSE);
  }

  public Boolean existsLeagueInCountryId(Long countryId) {
    Optional<League> leagueOptional = leagueRepository.findByCountryId(countryId);
    return leagueOptional.isPresent() ? Boolean.TRUE : Boolean.FALSE;
  }
}
