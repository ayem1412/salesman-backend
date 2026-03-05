package ma.ismagi.identityservice.model;

import lombok.Getter;

@Getter
public enum Role {
  ADMIN,
  SALES_REP;

  private String name;
}
