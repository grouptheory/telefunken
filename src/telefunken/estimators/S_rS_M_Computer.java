/*
 * TELEFUNKEN POPULATION ESTIMATOR
 * Khan, Lee, Dombrowski, Fellows
 * @2017 All rights reserved
 */
package telefunken.estimators;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import java.util.HashMap;
import java.util.Set;
import telefunken.core.Vertex;
import telefunken.hashers.IHasher;
import telefunken.samplers.AbstractSample;

/**
 *
 * @author Bilal Khan
 */
public class S_rS_M_Computer 
{
    public final Multiset<Integer> S;
    public final Multiset<Integer> rS;
    public final Multiset<Integer> M;
    
    public S_rS_M_Computer(AbstractSample sample, IHasher hash) 
    {
        Multiset<Vertex> Vsample = HashMultiset.create();
        Vsample.addAll(sample.getVertices());
        
        S = hash.getCodes(Vsample);
        rS = sample.getReportsAsCodes(hash) ;
        
        M = HashMultiset.create();
        for (Integer x : rS) {
            if (S.contains(x)) {
                M.add(x);
            }
        }
    }
}
