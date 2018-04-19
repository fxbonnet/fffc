/*
 * Copyright 2018 Octo Technologies.
 */
package com.octo.fixedfileformatconverter.exceptions;

/**
 *
 * @author Mark Zsilavecz
 */
public class InvalidColumnFormatException extends RuntimeException
{

  /**
   * Creates a new instance of <code>InvalidColumnFormatException</code> without detail message.
   */
  public InvalidColumnFormatException()
  {
  }


  /**
   * Constructs an instance of <code>InvalidColumnFormatException</code> with the specified detail message.
   *
   * @param msg the detail message.
   */
  public InvalidColumnFormatException(String msg)
  {
    super(msg);
  }
}
