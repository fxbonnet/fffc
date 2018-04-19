/**
 * Copyright 2018 Octo Technologies.
 */
package com.octo.fixedfileformatconverter.impl;

import java.util.Objects;

/**
 * Pair.
 *
 * Holds a pair of objects.
 *
 * @author Mark Zsilavecz
 *
 * @param <T> first element type.
 * @param <U> second element type.
 */
public class Pair<T, U>
{

    private final T first;
    private final U second;

    /**
     * @param first first element in the pair.
     * @param second second element in the pair.
     */
    public Pair(T first, U second)
    {
        this.first = first;
        this.second = second;
    }

    /**
     * @return the first element in the pair.
     */
    public T getFirst()
    {

        return first;
    }

    /**
     * @return the second element in the pair
     */
    public U getSecond()
    {
        return second;
    }

    @Override
    public String toString()
    {
        return "<" + first + ", " + second + ">";
    }

    @Override
    public int hashCode()
    {
        int hash = 19;
        hash = 53 * hash + Objects.hashCode(this.first);
        hash = 53 * hash + Objects.hashCode(this.second);
        return hash;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        final Pair<?, ?> other = (Pair<?, ?>) o;
        return (Objects.equals(this.first, other.first)
                && Objects.equals(this.second, other.second));
    }

}
