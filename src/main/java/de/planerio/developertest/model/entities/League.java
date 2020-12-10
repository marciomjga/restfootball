package de.planerio.developertest.model.entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
@Entity
public class League {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @OneToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "countryId", insertable = false, updatable = false)
  private Country country;

  @Column(nullable = false)
  private Long countryId;

  @OneToMany(mappedBy = "league", fetch = FetchType.LAZY)
  private List<Team> teams;
}
