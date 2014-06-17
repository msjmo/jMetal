//  Problem.java
//
//  Authors:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
// 
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

package org.uma.jmetal.core;

import org.uma.jmetal.problem.ZDT.ZDT4;
import org.uma.jmetal.util.JMetalException;

import java.io.Serializable;
import java.util.Arrays;
import com.google.inject.*;

/**
 * Abstract class representing a multiobjective optimization problem
 */
@ImplementedBy(ZDT4.class)
public abstract class Problem implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 7027317068597003106L;

  /**
   * Defines the default precision of binary-coded variables
   */
  private static final int DEFAULT_PRECISSION = 16;

  /**
   * Stores the number of variables of the problem
   */
  protected int numberOfVariables_;

  /**
   * Stores the number of objectives of the problem
   */
  protected int numberOfObjectives_;

  /**
   * Stores the number of constraints of the problem
   */
  protected int numberOfConstraints_;

  /**
   * Stores the problem name
   */
  protected String problemName_;

  /**
   * Stores the type of the solutions of the problem
   */
  protected SolutionType solutionType_;

  /**
   * Stores the lower bound values for each encoding.variable (only if needed)
   */
  protected double[] lowerLimit_;

  /**
   * Stores the upper bound values for each encoding.variable (only if needed)
   */
  protected double[] upperLimit_;

  /**
   * Stores the number of bits used by binary-coded variables (e.g., BinaryReal
   * variables). By default, they are initialized to DEFAULT_PRECISION)
   */
  protected int[] precision_;

  /**
   * Stores the length of each encoding.variable when applicable (e.g., Binary
   * and Permutation variables)
   */
  protected int[] length_;

  /**
   * Constructor.
   */
  public Problem() {
    solutionType_ = null;
  }

  /**
   * Constructor.
   */
  public Problem(SolutionType solutionType) {
    solutionType_ = solutionType;
  }

  /**
   * Gets the number of decision variables of the problem.
   *
   * @return the number of decision variables.
   */
  public int getNumberOfVariables() {
    return numberOfVariables_;
  }

  /**
   * Sets the number of decision variables of the problem.
   */
  public void setNumberOfVariables(int numberOfVariables) {
    numberOfVariables_ = numberOfVariables;
  }

  /**
   * Gets the the number of objectives of the problem.
   *
   * @return the number of objectives.
   */
  public int getNumberOfObjectives() {
    return numberOfObjectives_;
  }

  /**
   * Gets the lower bound of the ith encoding.variable of the problem.
   *
   * @param i The index of the encoding.variable.
   * @return The lower bound.
   */
  public double getLowerLimit(int i) {
    return lowerLimit_[i];
  }

  /**
   * Gets the upper bound of the ith encoding.variable of the problem.
   *
   * @param i The index of the encoding.variable.
   * @return The upper bound.
   */
  public double getUpperLimit(int i) {
    return upperLimit_[i];
  }

  /**
   * Evaluates a <code>Solution</code> object.
   *
   * @param solution The <code>Solution</code> to evaluate.
   */
  public abstract void evaluate(Solution solution) throws JMetalException;

  /**
   * Gets the number of side constraints in the problem.
   *
   * @return the number of constraints.
   */
  public int getNumberOfConstraints() {
    return numberOfConstraints_;
  }

  /**
   * Evaluates the overall constraint violation of a <code>Solution</code>
   * object.
   *
   * @param solution The <code>Solution</code> to evaluate.
   */
  public void evaluateConstraints(Solution solution) throws JMetalException {
    // The default behavior is to do nothing. Only constrained problem have to
    // re-define this method
  }

  /**
   * Returns the number of bits that must be used to encode binary-real
   * variables
   *
   * @return the number of bits.
   */
  public int getPrecision(int var) {
    return precision_[var];
  }

  /**
   * Returns array containing the number of bits that must be used to encode
   * binary-real variables.
   *
   * @return the number of bits.
   */
  public int[] getPrecision() {
    return precision_;
  }

  /**
   * Sets the array containing the number of bits that must be used to encode
   * binary-real variables.
   *
   * @param precision The array
   */
  public void setPrecision(int[] precision) {
    precision_ = Arrays.copyOf(precision, precision.length);
  }

  /**
   * Returns the length of the encoding.variable.
   *
   * @return the encoding.variable length.
   */
  public int getLength(int var) {
    if (length_ == null) {
      return DEFAULT_PRECISSION;
    }
    return length_[var];
  }

  /**
   * Returns the type of the variables of the problem.
   *
   * @return type of the variables of the problem.
   */
  public SolutionType getSolutionType() {
    return solutionType_;
  }

  /**
   * Sets the type of the variables of the problem.
   *
   * @param type The type of the variables
   */
  public void setSolutionType(SolutionType type) {
    solutionType_ = type;
  } // setSolutionType

  /**
   * Returns the problem name
   *
   * @return The problem name
   */
  public String getName() {
    return problemName_;
  }

  /**
   * Returns the number of bits of the solutions of the problem
   *
   * @return The number of bits solutions of the problem
   */
  public int getNumberOfBits() {
    int result = 0;
    for (int var = 0; var < numberOfVariables_; var++) {
      result += getLength(var);
    }
    return result;
  }
}