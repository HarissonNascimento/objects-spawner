package com.github.harissonnascimento.classes;

import java.util.List;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Employee {

  private String stringTest;

  private char charTest;
  private char charTestDuplicate;
  private Character wrapperCharTest;

  private int intTest;
  private Integer wrapperIntTest;

  private List<Person> listTest;

  private Set<Person> setTest;

  private double doubleTest;
  private Double wrapperDoubleTest;

  private float floatTest;
  private Float wrapperFloatTest;

  private long longTest;
  private Long wrapperLongTest;

  private short shortTest;
  private Short wrapperShortTest;

  private boolean booleanTest;
  private Boolean wrapperBooleanTest;

  private byte byteTest;
  private Byte wrapperByteTest;

  private Person otherObjectTest;

}
