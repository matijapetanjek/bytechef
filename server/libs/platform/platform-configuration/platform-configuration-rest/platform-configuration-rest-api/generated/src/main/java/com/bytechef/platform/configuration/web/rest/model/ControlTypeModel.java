package com.bytechef.platform.configuration.web.rest.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * A type of the control to show in UI.
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-06-13T06:45:36.300303+02:00[Europe/Zagreb]", comments = "Generator version: 7.5.0")
public enum ControlTypeModel {
  
  ARRAY_BUILDER("ARRAY_BUILDER"),
  
  CODE_EDITOR("CODE_EDITOR"),
  
  DATE("DATE"),
  
  DATE_TIME("DATE_TIME"),
  
  EMAIL("EMAIL"),
  
  INTEGER("INTEGER"),
  
  FILE_ENTRY("FILE_ENTRY"),
  
  OBJECT_BUILDER("OBJECT_BUILDER"),
  
  MULTI_SELECT("MULTI_SELECT"),
  
  NUMBER("NUMBER"),
  
  PASSWORD("PASSWORD"),
  
  PHONE("PHONE"),
  
  SELECT("SELECT"),
  
  TEXT("TEXT"),
  
  TEXT_AREA("TEXT_AREA"),
  
  TIME("TIME"),
  
  URL("URL");

  private String value;

  ControlTypeModel(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static ControlTypeModel fromValue(String value) {
    for (ControlTypeModel b : ControlTypeModel.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

