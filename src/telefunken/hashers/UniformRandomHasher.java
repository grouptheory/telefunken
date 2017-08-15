/*
 * TELEFUNKEN POPULATION ESTIMATOR
 * Khan, Lee, Dombrowski, Fellows
 * @2017 All rights reserved
 */
package telefunken.hashers;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import telefunken.core.Vertex;
import telefunken.utils.Assertion;
import telefunken.math.Randomness;

/**
 *
 * @author Bilal Khan
 */
public final class UniformRandomHasher extends AbstractHasher {

    private final int _H;
    private final HashMap<Vertex, Integer> _vertex2code = new HashMap<Vertex, Integer>();
    private final HashMap<Integer,HashSet<Vertex>> _vertices2merge = new HashMap<Integer,HashSet<Vertex>>();
       
    public UniformRandomHasher(int H) {
        _H = H;
    }
    
    public int getHashspaceSize() {
        return _H;
    }
    
    private boolean _initialized = false;
    @Override
    public void initialize(Collection<Vertex> vset) {
        _vertex2code.clear();
        _vertices2merge.clear();
        for (Vertex v : vset) {
            int code = Randomness.getInteger() % _H;
            HashSet<Vertex> mergeset;
            if (_vertices2merge.containsKey(code)) {
                mergeset=_vertices2merge.get(code);
            }
            else {
                mergeset = new HashSet<Vertex>();
                _vertices2merge.put(code,mergeset);
            }
            mergeset.add(v);
            _vertex2code.put(v,code);
        }
        _initialized = true;
    }
    
    @Override
    public Integer getCode(Vertex v) {
        Assertion.test(_initialized, "Use of uninitialized UniformRandomHasher");
        return _vertex2code.get(v);
    }
    
    @Override
    public Collection<Vertex> getVerticesWithCode(int code) {
        Assertion.test(_initialized, "Use of uninitialized LosslessHasher");
        HashSet<Vertex> mergeset;
        if (_vertices2merge.containsKey(code)) {
            mergeset=_vertices2merge.get(code);
        }
        else {
            mergeset = new HashSet<Vertex>();
        }
        return mergeset;
    }

    @Override
    public boolean isLossy() {
        return true;
    }
}
