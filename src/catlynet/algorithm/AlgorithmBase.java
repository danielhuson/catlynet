/*
 * AlgorithmBase.java Copyright (C) 2019. Daniel H. Huson
 *
 *  (Some files contain contributions from other authors, who are then mentioned separately.)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package catlynet.algorithm;

import catlynet.model.MoleculeType;
import catlynet.model.Reaction;
import catlynet.model.ReactionSystem;
import jloda.util.Basic;
import jloda.util.CanceledException;
import jloda.util.ProgressListener;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * computes a new reaction model from an old one
 * Daniel Huson, 7.2019
 */
public abstract class AlgorithmBase {
    /**
     * run the algorithm
     *
     * @param input
     * @param progress
     * @return output
     */
    abstract public ReactionSystem apply(ReactionSystem input, ProgressListener progress) throws CanceledException;

    /**
     * add molecules mentioned as products to the given list of existing molecules
     *
     * @param molecules existing molecules
     * @param reactions
     * @return extended food set
     */
    protected Set<MoleculeType> addAllMentionedProducts(Collection<MoleculeType> molecules, Collection<Reaction> reactions) {
        final Set<MoleculeType> products = new TreeSet<>(molecules);
        for (Reaction reaction : reactions) {
            products.addAll(reaction.getProducts());
            }
        return products;
    }

    /**
     * gets the closure of the set of molecules with respect to the set of reactions, ignoring catalysts and inhibitors
     *
     * @param molecules existing molecules
     * @param reactions
     * @return extended food set
     */
    protected Set<MoleculeType> computeClosure(Collection<MoleculeType> molecules, Collection<Reaction> reactions) {
        final Set<MoleculeType> allMolecules = new TreeSet<>(molecules);
        boolean changed;
        do {
            changed = false;
            for (Reaction reaction : reactions) {
                if (allMolecules.containsAll(reaction.getReactants())) {
                    if (!allMolecules.containsAll(reaction.getProducts())) {
                        allMolecules.addAll(reaction.getProducts());
                        changed = true;
                    }
                }
            }
        }
        while (changed);
        return allMolecules;
    }

    /**
     * filter reactions to only keep those that can be run given the current food
     *
     * @param food
     * @param reactions
     * @return filtered reactions
     */
    protected Set<Reaction> filterReactions(Collection<MoleculeType> food, Collection<Reaction> reactions) {
        return reactions.stream().filter(r -> food.containsAll(r.getReactants()) && (r.getCatalysts().size() == 0 || Basic.intersects(food, r.getCatalysts()))).collect(Collectors.toSet());
    }
}
