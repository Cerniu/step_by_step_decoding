package net.cernius.kodt;

import net.cernius.kodt.datastructures.Bit;

import java.util.List;

/**
 * Created by tomas on 11/6/17.
 */
public interface Channel {
    List<Bit> send(List<Bit> data);
}
